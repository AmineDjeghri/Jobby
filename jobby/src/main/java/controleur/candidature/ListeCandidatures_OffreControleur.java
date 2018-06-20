/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.candidature;


import entities.Candidature;
import entities.Competence_cv;
import entities.Competence_offre;
import entities.Langue_cv;
import entities.Langue_offre;
import entities.Offre;
import entities.Recruteur;
import entities.SecteurActivite;
import enumerations.NiveauEtudeEnum;
import enumerations.SituationFamilialeEnum;
import enumerations.TypeReponseEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import services.CandidatureFacade;
import services.OffreFacade;
import services.RecruteurFacade;
import services.SecteurActiviteFacade;
import utilities.RankedItem;

/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeCandidatures_OffreControleur implements Serializable{
    
    @EJB
    private CandidatureFacade candidatureFacade;
    @EJB
    private RecruteurFacade recruteurFacade;
    @EJB
    private OffreFacade offreFacade;
    @EJB
    private SecteurActiviteFacade secteurActiviteFacade;
    
    private Recruteur recruteur;
    private int offreId;
    private Candidature selectedCandidature;
    private ArrayList<Candidature> listeCandidatures;
    
    
    
    private ArrayList<SecteurActivite> listeSecteursActivite;
    private NiveauEtudeEnum niveauEtudeEnums [];
    private SituationFamilialeEnum  [] situationFamilialeEnums;
    //variables de filtre
    private SecteurActivite filteredSecteurActivite;
    private NiveauEtudeEnum filtredNiveauEtude;
    private SituationFamilialeEnum filterSituationFamiliale;
    private Integer filterAnneeExpMin;
    private Integer filterAnneeExpMax;
    private Boolean filterServiceMilitaire;
    
    
    //Electre 3 matrices 
    float [][]matricePerformance;
    float [][]matriceQPVW;
    float [][]matriceConcordance; 
    float [][]matriceCredibility;
    ArrayList<float [][]> matricesDiscordance;
    
    //poids des criteres
    private int w1=1,w2=1,w3=1,w4=1; //w1 pour la l'experience,w2 pour le niveau d'étude ,w3 pour la langue,w4 pour la competence
    private int nbrCriteres;
    private int nbrCandidatures;
    private Offre offre;

    
    @PostConstruct
    public void init(){
        
        try{
            listeSecteursActivite=new ArrayList<>(secteurActiviteFacade.findAll());
            niveauEtudeEnums=NiveauEtudeEnum.values();
            situationFamilialeEnums=SituationFamilialeEnum.values();
            
            
            
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            recruteur=(Recruteur)session.getAttribute("membre");

            recruteur=recruteurFacade.find(recruteur.getId());
            offreId=Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("offreId"));          
            if(offreId!=0){
            listeCandidatures=candidatureFacade.findCandidaturesByOffre(offreId);
            nbrCandidatures=listeCandidatures.size();

            offre=(Offre)offreFacade.find(offreId);
            System.out.println("offre object "+offreId);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("offre id "+offreId);
    }

    
    public void filter(){
        System.out.println("selected secteur "+filteredSecteurActivite);
        System.out.println("selected niveau etude "+filtredNiveauEtude);
        System.out.println("selected situation familiale "+filterSituationFamiliale);
        System.out.println("selected annee exp min "+filterAnneeExpMin);
        System.out.println("selected annee exp max "+filterAnneeExpMax);
        System.out.println("selected service militaire "+filterServiceMilitaire);
        
        if(offreId!=0)
        listeCandidatures=candidatureFacade.filterCandidaturesByOffreAndParams( offreId,filteredSecteurActivite,filtredNiveauEtude, filterSituationFamiliale,filterServiceMilitaire,filterAnneeExpMin, filterAnneeExpMax);
        
    }
    
    
    public void accepter(){
        System.out.println("selected "+selectedCandidature);
        if(selectedCandidature!=null){
            selectedCandidature.setTypeReponse(TypeReponseEnum.Accepted);
            try{
                candidatureFacade.edit(selectedCandidature);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
        public void refuser(){
        if(selectedCandidature!=null){
            selectedCandidature.setTypeReponse(TypeReponseEnum.refused);
            try{
                candidatureFacade.edit(selectedCandidature);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void mettreEntAttente(){
        if(selectedCandidature!=null){
            selectedCandidature.setTypeReponse(TypeReponseEnum.waiting);
            try{
                candidatureFacade.edit(selectedCandidature);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
        
    public boolean checkWaiting(){
        if(selectedCandidature!=null)
        if(selectedCandidature.getTypeReponse()== TypeReponseEnum.waiting)
            return true;
        return false;
    } 
    
    public boolean checkAccepted(){
        if(selectedCandidature!=null)
        if(selectedCandidature.getTypeReponse()== TypeReponseEnum.Accepted)
            return true;
        return false;
    }   
    public boolean checkRefused(){
        if(selectedCandidature!=null)
        if(selectedCandidature.getTypeReponse()== TypeReponseEnum.refused)
            return true;
        return false;
    }       

    
        //Electre 3 matrices



     public  void trier(){
         System.out.println("POIDS -- w1 w2 w3"+w1+" "+w2+ " "+w3);
         
        if(listeCandidatures.isEmpty() || offre==null)
            return ;
        
        matricePerformance=new float[0][0];
        matriceConcordance=new float[0][0];
        matricesDiscordance=new ArrayList<float [][]>();
        ArrayList<RankedItem> finalRankedItems=new ArrayList<>();
        
        ArrayList<ArrayList<Float>>rankDescendant=new ArrayList<>();
        ArrayList<ArrayList<Float>>rankAscendant=new ArrayList<>();
        
        
        matricePerformance=MatricePerformance();
        matriceQPVW=new float[][]{{ new Float(0.1),new Float(0.1),new Float(0.1)},{ new Float(0.15),new Float(0.15),new Float(0.15)},{ new Float(0.5),new Float(0.5),new Float(0.5)},{w1,w2,w3}};
        matriceConcordance=new float[matricePerformance.length][matricePerformance.length];
        matriceCredibility=new float[matricePerformance.length][matricePerformance.length];

         
        
        if(matricePerformance.length!=0){
            calculerConcordance();
            calculerDiscordance();
            calculerCredibility();

            float [][]matriceCredibilityForDes=copyMatrice(matriceCredibility);
            float [][]matriceCredibilityForAsc=copyMatrice(matriceCredibility);

            for (int i = 0; i < matriceCredibility.length; i++) {
            for (int j = 0; j < matriceCredibility[i].length; j++) {
                String formattedString = String.format("%.02f", matriceCredibility[i][j]);
            System.out.print(formattedString+"  ");
            }
             System.out.println();
            }


             rankDescendant = rankDescendant(matriceCredibilityForDes);
             System.out.println("----------------------------------------------------------------------------------2ND ALGO");
             afficherMatrice(matriceCredibilityForAsc);
            rankAscendant=rankAscendant(matriceCredibilityForAsc);

             System.out.println("liste rang descedant "+rankDescendant);
             System.out.println("liste rang descedant "+rankAscendant);
             finalRankedItems=finalRank(rankDescendant, rankAscendant);  
             //Ranking the Cvs 
            ArrayList<Candidature> listeCandidaturesTemp=new ArrayList<>(); 
            for(int i=0;i<listeCandidatures.size();i++)
                listeCandidaturesTemp.add(listeCandidatures.get(finalRankedItems.get(i).getiAlternative()));
            
            listeCandidatures=listeCandidaturesTemp;
            System.out.println("CV Triés "+listeCandidatures);
            System.out.println("POIDS -- w1 w2 w3"+w1+" "+w2+" "+w3);
        }
    }

    private  float[][] MatricePerformance(){
    //for Tests
    //    float [][]matricePerformance= new float[][]{ { 12,11,2 },{14,13,2},{16,15,2},{20,15,2},{13,13,2}};
    
    int nbrCritere=1;
    int indiceL=-1;
    int indiceC=-1;
    

    //put the Cvs into the performance matrix
    System.out.println("size liste"+listeCandidatures.size());
    if(listeCandidatures.size()!=0){
        System.out.println("size langue "+offre.getListeLangues_offre().size());
        System.out.println("size competence "+offre.getListeCompetences_offre().size());
        
        
        if(!offre.getListeLangues_offre().isEmpty() && !offre.getListeCompetences_offre().isEmpty()){          
            indiceC=nbrCritere;
            indiceL=nbrCritere+1;
            nbrCritere=nbrCritere+2;
                    System.out.println("nbr critere: "+nbrCritere);
        }
         
        else if(!offre.getListeLangues_offre().isEmpty()){
            System.out.println("dans langue");
            indiceL=nbrCritere;
            nbrCritere++;
                    System.out.println("nbr critere: "+nbrCritere);
        }
            
        else if(!offre.getListeCompetences_offre().isEmpty() ){
            System.out.println("dans offre");
            indiceC=nbrCritere;
            nbrCritere++;
                    System.out.println("nbr critere: "+nbrCritere);
        }
        System.out.println("nbr critere: "+nbrCritere);
        System.out.println("indice Langue: "+indiceL);
        System.out.println("indice Competence: "+indiceC);
        
        matricePerformance=new float[listeCandidatures.size()][nbrCritere];
        
        if(nbrCritere!=0)
        {   for(int i=0;i<listeCandidatures.size();i++) 
                if(listeCandidatures.get(i).getCv().getAnneeExperience()!=null)
                    matricePerformance[i][0]=calculerNoteAnneeExperience(listeCandidatures.get(i));
                else matricePerformance[i][0]=0;
             
        
            if(! offre.getListeLangues_offre().isEmpty() )
                for(int i=0;i<listeCandidatures.size();i++){
                    matricePerformance[i][indiceL]=calculerNoteLangue(listeCandidatures.get(i));
                    System.out.println(" "+matricePerformance[i][indiceL]);
                }    
            if(! offre.getListeCompetences_offre().isEmpty() )
            for(int i=0;i<listeCandidatures.size();i++){
                    matricePerformance[i][indiceC]=calculerNoteCompetence(listeCandidatures.get(i));
                    System.out.println("  "+matricePerformance[i][indiceC]);
            }
        }

    }
    else matricePerformance=new float[0][0];
        System.out.println("aa");
        afficherMatrice(matricePerformance);
    return matricePerformance;
    }

    private  void calculerConcordance (){
       float w=0;

        //some des poids
        for(int i=0;i<matriceQPVW[3].length;i++)
        w=w+matriceQPVW[3][i];
        System.out.println("----- poids total "+w);

        for(int i=0;i<matricePerformance.length;i++)
            for(int k=0;k<matricePerformance.length;k++){
                float c=0;
               for(int j=0;j<matricePerformance[i].length;j++)
                {   float cj=0;float ga=matricePerformance[i][j];float gb=matricePerformance[k][j];float q=matriceQPVW[0][j];float p=matriceQPVW[1][j];float wj=matriceQPVW[3][j];
                if(ga+q>=gb) cj=1;
                    else if(ga+p<gb) cj=0;
                    else cj=(p+ga-gb)/(p-q);
                    c=cj*wj+c;
                }
              matriceConcordance[i][k]=c/w;

            }
   }

    private  void calculerDiscordance(){
        matricesDiscordance=new ArrayList<>();
        for(int k=0;k<matricePerformance[0].length;k++){
        float [][]matriceDiscordance=new float[matricePerformance.length][matricePerformance.length];
        matricesDiscordance.add(matriceDiscordance);
        }


        //creer k fois la matrices de discordance (k = nbr de critere) et l'ajouter à la liste qui contient les matrices de discordance

            for(int j=0;j<matricePerformance[0].length;j++){
                for(int i=0;i<matricePerformance.length;i++)
                for(int l=0;l<matricePerformance.length;l++){
                float dj=0;float ga=matricePerformance[i][j];float gb=matricePerformance[l][j];float p=matriceQPVW[1][j];float vj=matriceQPVW[2][j];
                if(ga+p>=gb) dj=0;
                else if(ga+vj<=gb) dj=1;
                else dj=(gb-ga-p)/(vj-p);
                matricesDiscordance.get(j)[i][l]=dj;
                }
        }

    }

    private  void calculerCredibility(){
        System.out.println("size array "+matricesDiscordance.size());

        float s=0;
        for(int i=0;i<matriceConcordance.length;i++)
            for(int j=0;j<matriceConcordance[i].length;j++){
                int k=0;
                while(k<matricesDiscordance.size() && matriceConcordance[i][j]>=matricesDiscordance.get(k)[i][j])
                {k++;
                }
                if(k>=matricesDiscordance.size())
                  s=matriceConcordance[i][j];
                else{ s=matriceConcordance[i][j];
                    for(k=0;k<matricesDiscordance.size();k++)
                        if(matriceConcordance[i][j]<matricesDiscordance.get(k)[i][j])
                         s=s*((1-matricesDiscordance.get(k)[i][j])/(1-matriceConcordance[i][j]));
                }
                    matriceCredibility[i][j]=s;
            }


    }

    public  ArrayList<ArrayList<Float>> rankDescendant(float[][]matriceCredibility){
        float lambda0;
        float lambda1;
        float lambda2;
        float lambda3;
        float sLambda;
        float[][]A0;
        float[][]D0;
        float maxQ;

        ArrayList<ArrayList<Float>>RankD=new ArrayList<>();
        A0=matriceCredibility;
        
        //voir si la liste elle contient déja ça
        ArrayList<Float>D2=new ArrayList<>();
        do{
        ArrayList<Float>D=new ArrayList<>();

        ArrayList<Float>[][]matriceS ;
        float[][]matricePFQ;

        
        int cpt=0;
        
        lambda0=lambdaMaxMatrice(A0);   
        D0=A0;
        do{
        
        lambda0=lambdaMaxMatrice(D0);    
        lambda0=Math.round(lambda0 * new Float(100)) / new Float(100); 
        
        sLambda=new Float(0.30)-new Float(0.15)*lambda0; 
        sLambda=Math.round(sLambda * new Float(100)) / new Float(100);
        System.out.println("s(lambda)="+sLambda);
        
        lambda1=lambda0-sLambda;
        lambda1=Math.round(lambda1 * new Float(100)) / new Float(100);
        System.out.println("lambda 1="+lambda1);
        
        lambda2=lambdaMaxMatriceAvecMax(A0,lambda1);
        System.out.println("lambda 2="+lambda2);
        

        //cette matrice(se compose de deux matrices) Objet contient les alternatives comme colonnes,et comme ligne: le S qui
        //est la liste des alternatives dont on est meilleur, la puissance P , la Faiblaisse F, et la Qualification Q
        matriceS=new ArrayList[1][D0[0].length];
        matricePFQ=new float[3][D0[0].length];
        if(cpt==0)
        matricePFQ=ajusterMatricePFQ(matricePFQ, RankD);
        else
            matricePFQ=ajusterMatricePFQd(matricePFQ, D);
        
        System.out.println("matrice s taille "+matriceS.length+" "+matriceS[0].length);
        System.out.println("matrice PFQ taille "+matricePFQ.length+" "+matricePFQ[0].length);
        
        
        //
        for(int m=0;m<matriceS.length;m++)
            for(int n=0;n<matriceS[m].length;n++)
                matriceS[m][n]=new ArrayList<>();

        for(int m=0;m<D0.length;m++)
            for(int n=0;n<D0[m].length;n++){
                float sAB=D0[m][n]; //s(a,b)
                float sBA=D0[n][m]; //s(b,a)
                
                if(sAB>lambda2 && (Math.abs(sAB-sBA)>=sLambda)&&(sAB!=-1)&&(sBA)!=-1 ){
                    matriceS[0][m].add(new Float(n));
                    matricePFQ[0][m]=matricePFQ[0][m]+1;
                    matricePFQ[1][n]=matricePFQ[1][n]-1;
                System.out.println("s(a,b)"+sAB +" "+m);
                System.out.println("s(b,a)"+sBA+" "+n);
                }
            }
        
       
        //calculer la Qualification
        for(int m=0;m<matricePFQ.length;m++)
            for(int n=0;n<matricePFQ[m].length;n++)
                if(matricePFQ[2][n]>-10000)
                matricePFQ[2][n]=matricePFQ[0][n]+matricePFQ[1][n];
                

        
        for (int m = 0; m < matriceS.length; m++) {
        for (int n = 0; n < matriceS[m].length; n++) {
        System.out.print(matriceS[m][n]+"  ");
        }
         System.out.println();
        }


        System.out.println("---------MATRICE PFQ");
        for (int m = 0; m < matricePFQ.length; m++) {
        for (int n = 0; n < matricePFQ[m].length; n++) {
        System.out.print(matricePFQ[m][n]+"  ");
        }
         System.out.println();
        }

        maxQ=maxQualification(matricePFQ[2]);
        System.out.println("max qualification "+maxQ);

        //ajouter les alternatives qui ont la plus grande qualification
        for (int m = 0; m < matricePFQ.length; m++)
        for (int n = 0; n < matricePFQ[m].length; n++)
            if(matricePFQ[2][n]==maxQ && !D.contains(new Float(n)))
                D.add(new Float(n));
        
        System.out.println("liste D"+D);
            lambda0=Math.round(lambda2 * new Float(100)) / new Float(100);
            System.out.println("lambda 0"+lambda0);
            
            if(D.size()!=1 && lambda2!=0)
                D0=recreerD0(D0,D);
            
             cpt++;
             System.out.println("source "+D);
             System.out.println("Destination "+D2);
             
             if(D.equals(D2))
                 lambda2=0;
             else{ D2=D;
                 Collections.copy(D2, D);
             }
        }while(D.size()!=1 && lambda2>0);
      
            for(int m=0;m<D.size();m++){
            System.out.println("d.geet0 "+D.get(m));
            A0=supprimerLigneColonne(A0,Math.round(D.get(m)));
           
            }
            afficherMatrice(A0);
             RankD.add(D);
             
        
        
            
        System.out.println("RANG DESCENDANT "+RankD);
        System.out.println("-------------ALMOST DONE WITH ETAPE 1--------------");
        }while(matriceNonVide(A0));
        
        return RankD;
    }
    
        public  ArrayList<ArrayList<Float>> rankAscendant(float[][]matriceCredibility){
        float lambda0;
        float lambda1;
        float lambda2;
        float lambda3;
        float sLambda;
        float[][]A0;
        float[][]D0;
        float maxQ;

        ArrayList<ArrayList<Float>>RankD=new ArrayList<>();
        A0=matriceCredibility;
        
        //voir si la liste elle contient déja ça
        ArrayList<Float>D2=new ArrayList<>();
        do{
        ArrayList<Float>D=new ArrayList<>();

        ArrayList<Float>[][]matriceS ;
        float[][]matricePFQ;

        
        int cpt=0;
        
        lambda0=lambdaMaxMatrice(A0);   
        D0=A0;
        do{
        
        lambda0=lambdaMaxMatrice(D0);    
        lambda0=Math.round(lambda0 * new Float(100)) / new Float(100); 
        
        sLambda=new Float(0.30)-new Float(0.15)*lambda0; 
        sLambda=Math.round(sLambda * new Float(100)) / new Float(100);
        System.out.println("s(lambda)="+sLambda);
        
        lambda1=lambda0-sLambda;
        lambda1=Math.round(lambda1 * new Float(100)) / new Float(100);
        System.out.println("lambda 1="+lambda1);
        
        lambda2=lambdaMaxMatriceAvecMax(A0,lambda1);
        System.out.println("lambda 2="+lambda2);
        

        //cette matrice(se compose de deux matrices) Objet contient les alternatives comme colonnes,et comme ligne: le S qui
        //est la liste des alternatives dont on est meilleur, la puissance P , la Faiblaisse F, et la Qualification Q
        matriceS=new ArrayList[1][D0[0].length];
        matricePFQ=new float[3][D0[0].length];
        if(cpt==0)
        matricePFQ=ajusterMatricePFQ(matricePFQ, RankD);
        else
            matricePFQ=ajusterMatricePFQd(matricePFQ, D);
        
        System.out.println("matrice s taille "+matriceS.length+" "+matriceS[0].length);
        System.out.println("matrice PFQ taille "+matricePFQ.length+" "+matricePFQ[0].length);
        
        
        //
        for(int m=0;m<matriceS.length;m++)
            for(int n=0;n<matriceS[m].length;n++)
                matriceS[m][n]=new ArrayList<>();

        for(int m=0;m<D0.length;m++)
            for(int n=0;n<D0[m].length;n++){
                float sAB=D0[m][n]; //s(a,b)
                float sBA=D0[n][m]; //s(b,a)

                if(sAB>lambda2 && (Math.abs(sAB-sBA)>sLambda)&&(sAB!=-1)&&(sBA)!=-1){
                    matriceS[0][m].add(new Float(n));
                    matricePFQ[0][m]=matricePFQ[0][m]+1;
                    matricePFQ[1][n]=matricePFQ[1][n]-1;
                System.out.println("s(a,b)"+sAB +" "+m);
                System.out.println("s(b,a)"+sBA+" "+n);
                }
            }
        
       
        //calculer la Qualification
        for(int m=0;m<matricePFQ.length;m++)
            for(int n=0;n<matricePFQ[m].length;n++)
                if(matricePFQ[2][n]>-10000)
                matricePFQ[2][n]=matricePFQ[0][n]+matricePFQ[1][n];
                

        
        for (int m = 0; m < matriceS.length; m++) {
        for (int n = 0; n < matriceS[m].length; n++) {
        System.out.print(matriceS[m][n]+"  ");
        }
         System.out.println();
        }


        System.out.println("---------MATRICE PFQ");
        for (int m = 0; m < matricePFQ.length; m++) {
        for (int n = 0; n < matricePFQ[m].length; n++) {
        System.out.print(matricePFQ[m][n]+"  ");
        }
         System.out.println();
        }

        maxQ=minQualification(matricePFQ[2]);
        System.out.println("min qualification "+maxQ);

        //ajouter les alternatives qui ont la plus grande qualification
        for (int m = 0; m < matricePFQ.length; m++)
        for (int n = 0; n < matricePFQ[m].length; n++)
            if(matricePFQ[2][n]==maxQ && !D.contains(new Float(n)))
                D.add(new Float(n));
        
        System.out.println("liste D"+D);
            lambda0=Math.round(lambda2 * new Float(100)) / new Float(100);
            System.out.println("lambda 0"+lambda0);
            
            if(D.size()!=1 && lambda2!=0)
                D0=recreerD0(D0,D);
            
             cpt++;
             System.out.println("source "+D);
             System.out.println("Destination "+D2);
             
             if(D.equals(D2))
                 lambda2=0;
             else{ D2=D;
                 Collections.copy(D2, D);
             }
        }while(D.size()!=1 && lambda2>0);
      
            for(int m=0;m<D.size();m++){
            System.out.println("d.geet0 "+D.get(m));
            A0=supprimerLigneColonne(A0,Math.round(D.get(m)));
           
            }
            afficherMatrice(A0);
             RankD.add(D);
             
        
        
            
        System.out.println("RANG ASCENDANT "+RankD);
        System.out.println("-------------ALMOST DONE WITH ETAPE 1--------------");
        }while(matriceNonVide(A0));
        
        return RankD;
    }
        
    public  ArrayList<RankedItem> finalRank(ArrayList<ArrayList<Float>>rankDescendant,ArrayList<ArrayList<Float>>rankAscendant){
        ArrayList<RankedItem> rankFinal=new ArrayList<>();
               
        Collections.reverse(rankAscendant);
               
        for(int i=0;i<rankAscendant.size();i++)
            for(int j=0;j<rankAscendant.get(i).size();j++){
                for(int k=0;k<rankDescendant.size();k++)
                    for(int l=0;l<rankDescendant.get(k).size();l++){
                        float a1=rankDescendant.get(k).get(l);
                        float a2=rankAscendant.get(i).get(j);
                        if(a1==a2){
                            float a=(new Float(i)+new Float(k))/new Float(2.0);
                            System.out.println(a);
                            rankFinal.add(new RankedItem(Math.round(a1),a));
                        
                            System.out.println(a1+" "+a2+" i "+i+" k "+k);
                        }
                    }
                        
            }
        Collections.sort(rankFinal);
                
        System.out.println("rank final "+rankFinal);
        return rankFinal;
        
    }




    public float lambdaMaxMatrice(float[][]matrice){
        float max=-1;

        for(int i=0;i<matrice.length;i++)
            for(int j=0;j<matrice[i].length;j++)
                if(i!=j)
                    if(matrice[i][j]>max && matrice[i][j]!=-1)
                        max=matrice[i][j];

        System.out.println("lambda0 "+max);
        return max;
    }
    public  float lambdaMaxMatriceAvecMax(float[][]matrice,float max){
         float max2=-1;

        for(int i=0;i<matrice.length;i++)
            for(int j=0;j<matrice[i].length;j++)
                if(i!=j)
                    if(matrice[i][j]>max2 && matrice[i][j]<max && matrice[i][j]!=-1){
                        max2=matrice[i][j];

                    }
        return max2;
    }

    public float maxQualification(float[]matrice){
        float max=matrice[0];

        for(int i=0;i<matrice.length;i++)
            if(matrice[i]>max)
                max=matrice[i];

        return max;
    }
    
    public float [][] supprimerLigneColonne(float [][]matrice,int num){
        for(int i=0;i<matrice.length;i++)
            for(int j=0;j<matrice[i].length;j++)
                if(j==num )
                matrice[i][j]=-1;

        return matrice;
    }
    
    
    public void afficherMatrice(float[][]matrice){
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                String formattedString = String.format("%.02f",matrice[i][j]);
            System.out.print(formattedString+"  ");
            }
             System.out.println();
            }
    }
    
    
    
    public boolean matriceNonVide(float [][] matrice){
        for(int i=0;i<matrice.length;i++)
            for(int j=0;j<matrice[i].length;j++)
                if(matrice[i][j]!=-1)
                    return true;
        return false;
        
    }
    
    public float[][]ajusterMatricePFQ(float [][]matrice,ArrayList<ArrayList<Float>>liste){
        
        System.out.println("liste AJUSTER MATRICE"+liste);
            for(int j=0;j<matrice[2].length;j++)
                for(int i=0;i<liste.size();i++)
                        if(liste.get(i).contains(new Float(j)))
                    matrice[2][j]=new Float(-100000);

        return matrice;
    }
    
    public float[][]ajusterMatricePFQd(float [][]matrice,ArrayList<Float>liste){
        
        System.out.println("liste AJUSTER MATRICE"+liste);
            for(int j=0;j<matrice[2].length;j++)
                        if(!liste.contains(new Float(j)))
                    matrice[2][j]=new Float(-100000);

        return matrice;
    }
    
    public float[][]recreerD0(float[][]matrice,ArrayList<Float>D){
        
        System.out.println("liste recreer "+D);
        for(int i=0;i<matrice.length;i++)
            for(int j=0;j<matrice[i].length;j++)
                if(!D.contains(new Float(i)))
                    matrice[i][j]=-1;
                        
        afficherMatrice(matrice);
        return matrice;
    }
    
    public float minQualification(float []matrice){
        
                float min=+10000;

        for(int i=0;i<matrice.length;i++)
            if(matrice[i]<min && matrice[i]>-1000)
                min=matrice[i];

        return min;
    }

    public float [][] copyMatrice(float [][]matrice){
    
        float [][]matriceDst=new float[matrice.length][matrice[0].length];
        
        for(int i=0;i<matrice.length;i++)
            for(int j=0;j<matrice[i].length;j++)
                matriceDst[i][j]=new Float(matrice[i][j]);
        
        return matriceDst;
    }
  
    public  ArrayList<Float> copyList(ArrayList<Float>listeSrc){
        ArrayList<Float>listeDst=new ArrayList<>();
        
        for(int i=0;i<listeSrc.size();i++){
            listeDst.add(new Float(listeSrc.get(i)));
        }    
        
        return listeDst;
    }
    
    
    
    public float calculerNoteLangue(Candidature candidature){
        float note=0;
        float poids=0;
        
        if(candidature.getOffre()!=null){
            offre=candidature.getOffre();
            for(Langue_offre langue_offre:offre.getListeLangues_offre()){
                for(Langue_cv langue_cv:candidature.getCv().getListeLangues_cv()){
                    if(langue_offre.getLangueEnum()==langue_cv.getLangueEnum())
                        note=note+langue_offre.getNiveauLangue()*langue_cv.getNiveauLangue();
                }
            poids=poids+5*langue_offre.getNiveauLangue();
            }        
            note=note/poids;
        }
        return note;
    }
    public float calculerNoteCompetence(Candidature candidature){
        float note=0;
        float poids=0;
        
        if(candidature.getOffre()!=null){
            offre=candidature.getOffre();
            for(Competence_offre competence_offre:offre.getListeCompetences_offre()){
                for(Competence_cv competence_cv:candidature.getCv().getListeCompetences_cv()){
                    if(competence_offre.getCompetence().equals(competence_cv.getCompetence()))
                        note=note+competence_offre.getNiveauCompetence()*competence_cv.getNiveauCompetence();
                }
            poids=poids+5*competence_offre.getNiveauCompetence();
            }        
            note=note/poids;
        }
        System.out.println("note "+note);
        return note;
    }
    
    public float calculerNoteAnneeExperience(Candidature candidature){
        float note=0;
        
        if(candidature.getOffre()!=null){
            offre=candidature.getOffre();
            if(candidature.getCv().getAnneeExperience()>=6)
                note=1;
            else{
                float note1=candidature.getCv().getAnneeExperience()*offre.getAnneeExperience();
                float note2=6*offre.getAnneeExperience();
            note=(note1)/(note2);
            
            }
            System.out.println("note année experience "+note );
        }
       
        return note;
    }
    
public float calculerNoteNiveauEtude(Candidature candidature){
        float noteCandidat=0,noteOffre=0,note=0;
        if(candidature.getOffre()!=null && candidature.getCv()!=null){            
            switch(candidature.getCv().getNiveauEtudeEnum()){
                case PasDimplome :
                noteCandidat=1;
                break;
                case Secondaire :
                noteCandidat=2;
                break;
                case Terminal :
                noteCandidat=3;
                break;
                case Baccalaureat:
                noteCandidat=5;
                break;
                case Formation :
                noteCandidat=1;
                break;
                case UnivSansDiplome :
                noteCandidat=7;
                break;
                case Licence2 :
                noteCandidat=10;
                break;
                case Licence3 :
                noteCandidat=13;
                break;
                case Master1 :
                noteCandidat=15;
                break;
                case Master2 :
                noteCandidat=18;
                break;
                case Doctorat :
                noteCandidat=20;
                break;
                default: 
                noteCandidat=0;
                break;
            }          
            switch(candidature.getOffre().getNiveauEtudeEnum()){
                case PasDimplome :
                noteOffre=1;
                break;
                case Secondaire :
                noteOffre=2;
                break;
                case Terminal :
                noteOffre=3;
                break;
                case Baccalaureat:
                noteOffre=5;
                break;
                case Formation :
                noteOffre=1;
                break;
                case UnivSansDiplome :
                noteOffre=7;
                break;
                case Licence2 :
                noteOffre=10;
                break;
                case Licence3 :
                noteOffre=13;
                break;
                case Master1 :
                noteOffre=15;
                break;
                case Master2 :
                noteOffre=18;
                break;
                case Doctorat :
                noteOffre=20;
                break;
                default: 
                noteOffre=0;
                break;
            } 
        }
        note=(noteCandidat*noteOffre)/(20*noteOffre);
        System.out.println("NOTE NIVEAU ETUDE "+note+" cv année experience (juste pour detected le cv) "+candidature.getCv().getAnneeExperience());
        return note*100;
    }    
    
    
    public Candidature getSelectedCandidature() {
        return selectedCandidature;
    }

    public void setSelectedCandidature(Candidature selectedCandidature) {
        this.selectedCandidature = selectedCandidature;
    }

    public ArrayList<Candidature> getListeCandidatures() {
        return listeCandidatures;
    }

    public void setListeCandidatures(ArrayList<Candidature> listeCandidatures) {
        this.listeCandidatures = listeCandidatures;
    }

    public int getOffreId() {
        return offreId;
    }

    public void setOffreId(int offreId) {
        this.offreId = offreId;
    }

    public int getW1() {
        return w1;
    }

    public void setW1(int w1) {
        this.w1 = w1;
    }

    public int getW2() {
        return w2;
    }

    public void setW2(int w2) {
        this.w2 = w2;
    }

    public int getW3() {
        return w3;
    }

    public void setW3(int w3) {
        this.w3 = w3;
    }

    public int getW4() {
        return w4;
    }

    public void setW4(int w4) {
        this.w4 = w4;
    }

    public ArrayList<SecteurActivite> getListeSecteursActivite() {
        return listeSecteursActivite;
    }

    public void setListeSecteursActivite(ArrayList<SecteurActivite> listeSecteursActivite) {
        this.listeSecteursActivite = listeSecteursActivite;
    }

    public NiveauEtudeEnum[] getNiveauEtudeEnums() {
        return niveauEtudeEnums;
    }

    public void setNiveauEtudeEnums(NiveauEtudeEnum[] niveauEtudeEnums) {
        this.niveauEtudeEnums = niveauEtudeEnums;
    }

    public SituationFamilialeEnum[] getSituationFamilialeEnums() {
        return situationFamilialeEnums;
    }

    public void setSituationFamilialeEnums(SituationFamilialeEnum[] situationFamilialeEnums) {
        this.situationFamilialeEnums = situationFamilialeEnums;
    }

    public SecteurActivite getFilteredSecteurActivite() {
        return filteredSecteurActivite;
    }

    public void setFilteredSecteurActivite(SecteurActivite filteredSecteurActivite) {
        this.filteredSecteurActivite = filteredSecteurActivite;
    }

    public NiveauEtudeEnum getFiltredNiveauEtude() {
        return filtredNiveauEtude;
    }

    public void setFiltredNiveauEtude(NiveauEtudeEnum filtredNiveauEtude) {
        this.filtredNiveauEtude = filtredNiveauEtude;
    }

    public Integer getFilterAnneeExpMin() {
        return filterAnneeExpMin;
    }

    public void setFilterAnneeExpMin(Integer filterAnneeExpMin) {
        this.filterAnneeExpMin = filterAnneeExpMin;
    }

    public Integer getFilterAnneeExpMax() {
        return filterAnneeExpMax;
    }

    public void setFilterAnneeExpMax(Integer filterAnneeExpMax) {
        this.filterAnneeExpMax = filterAnneeExpMax;
    }

    public SituationFamilialeEnum getFilterSituationFamiliale() {
        return filterSituationFamiliale;
    }

    public void setFilterSituationFamiliale(SituationFamilialeEnum filterSituationFamiliale) {
        this.filterSituationFamiliale = filterSituationFamiliale;
    }

    public Boolean getFilterServiceMilitaire() {
        return filterServiceMilitaire;
    }

    public void setFilterServiceMilitaire(Boolean filterServiceMilitaire) {
        this.filterServiceMilitaire = filterServiceMilitaire;
    }

    

    
}
