/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.offre;

import entities.Competence;
import entities.Competence_offre;
import entities.Langue_offre;
import entities.Offre;
import entities.Recruteur;
import entities.SecteurActivite;
import enumerations.CompetenceEnum;
import enumerations.LangueEnum;
import enumerations.NiveauEtudeEnum;
import enumerations.TypeContratEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import services.CompetenceFacade;
import services.OffreFacade;
import services.RecruteurFacade;
import services.SecteurActiviteFacade;


@Named
@ViewScoped
public class CreerOffreControleur implements Serializable{
    @EJB
    private OffreFacade offreFacade;
    @EJB
    private RecruteurFacade recruteurFacade;
    @EJB
    private CompetenceFacade competenceFacade;
    @EJB
    private SecteurActiviteFacade secteurActiviteFacade;
    
    private Offre offre;
    private Recruteur recruteur;
    private LangueEnum selectedLangue;
    private ArrayList<LangueEnum> listeLangueEnum;
    private Competence selectedCompetence;
    private ArrayList<Competence> listeCompetences;
    private TypeContratEnum typeContratEnum[];
    private ArrayList<SecteurActivite> listeSecteursActivite;
    private NiveauEtudeEnum niveauEtudeEnum[];
    
    
    @PostConstruct
    public void init(){
        offre=new Offre();  
       
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try{
        recruteur=(Recruteur)session.getAttribute("membre");       
        recruteur=recruteurFacade.find(recruteur.getId());
        offre.setRecruteur(recruteur);
        
        listeLangueEnum=new ArrayList<>(Arrays.asList(LangueEnum.values()));
        listeCompetences=new ArrayList<>(competenceFacade.findAll());
        typeContratEnum=TypeContratEnum.values();
        listeSecteursActivite=new ArrayList(secteurActiviteFacade.findAll());
        niveauEtudeEnum=NiveauEtudeEnum.values();
        
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    public String creerOffre(){ 
        String url=null;
        try{
            offre.setDateCreation(new Date());
            if(offre.getActive()==true)
                offre.setDateActivation(new Date());
            System.out.println("date création offre "+offre.getDateCreation());
            offreFacade.create(offre);                          
            recruteur.getListeOffres().add(offre);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Création de l'offre avec succés"));
            url="/secured/espace-recruteur/mes-offres";
            
        }catch(Exception e){
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite",""));
        }
        return url;
    }
    
    public void addLangue(){               
        Langue_offre langue_offre=new Langue_offre();              
        langue_offre.setLangueEnum(selectedLangue);             
        langue_offre.setOffre(offre);
        offre.getListeLangues_offre().add(langue_offre);
        listeLangueEnum.remove(selectedLangue);
    }
    
    public void addCompetence(){
            
        Competence_offre competence_offre=new Competence_offre();
        selectedCompetence=competenceFacade.find(selectedCompetence.getId());
        competence_offre.setCompetence(selectedCompetence);
        competence_offre.setOffre(offre);
        offre.getListeCompetences_offre().add(competence_offre);
        listeCompetences.remove(selectedCompetence);
    }
    
    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public LangueEnum getSelectedLangue() {
        return selectedLangue;
    }

    public void setSelectedLangue(LangueEnum selectedLangue) {
        this.selectedLangue = selectedLangue;
    }

    public ArrayList<LangueEnum> getListeLangueEnum() {
        return listeLangueEnum;
    }

    public void setListeLangueEnum(ArrayList<LangueEnum> listeLangueEnum) {
        this.listeLangueEnum = listeLangueEnum;
    }

    public Competence getSelectedCompetence() {
        return selectedCompetence;
    }

    public void setSelectedCompetence(Competence selectedCompetence) {
        this.selectedCompetence = selectedCompetence;
    }

    public ArrayList<Competence> getListeCompetences() {
        return listeCompetences;
    }

    public void setListeCompetences(ArrayList<Competence> listeCompetences) {
        this.listeCompetences = listeCompetences;
    }

    public TypeContratEnum[] getTypeContratEnum() {
        return typeContratEnum;
    }

    public void setTypeContratEnum(TypeContratEnum[] typeContratEnum) {
        this.typeContratEnum = typeContratEnum;
    }

    public ArrayList<SecteurActivite> getListeSecteursActivite() {
        return listeSecteursActivite;
    }

    public void setListeSecteursActivite(ArrayList<SecteurActivite> listeSecteursActivite) {
        this.listeSecteursActivite = listeSecteursActivite;
    }

    public NiveauEtudeEnum[] getNiveauEtudeEnum() {
        return niveauEtudeEnum;
    }

    public void setNiveauEtudeEnum(NiveauEtudeEnum[] niveauEtudeEnum) {
        this.niveauEtudeEnum = niveauEtudeEnum;
    }
    
    
}
