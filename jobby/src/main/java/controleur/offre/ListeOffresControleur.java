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
import org.primefaces.event.CloseEvent;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import services.CompetenceFacade;
import services.OffreFacade;
import services.RecruteurFacade;
import services.SecteurActiviteFacade;

/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeOffresControleur implements Serializable{
    
    private Recruteur recruteur;
    private ArrayList<Offre> listeOffres;
    private Offre selectedOffre;
  
    private LangueEnum selectedLangue;
    private ArrayList<LangueEnum> listeLangueEnum;
    private Competence selectedCompetence;
    private ArrayList<Competence> listeCompetences;
    private TypeContratEnum typeContratEnum[];
    private ArrayList<SecteurActivite> listeSecteursActivite;
    private NiveauEtudeEnum niveauEtudeEnum[];
    
    
    @EJB
    private RecruteurFacade recruteurFacade;
    @EJB
    private OffreFacade offreFacade;
    @EJB
    private CompetenceFacade competenceFacade;
    @EJB
    private SecteurActiviteFacade secteurActiviteFacade;

    //google map
    private MapModel mapModel;    
    private Marker marker;
    
    @PostConstruct
    public void init(){          
        selectedOffre=new Offre();
        mapModel = new DefaultMapModel();
    try{        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        recruteur=(Recruteur)session.getAttribute("membre");
        recruteur=recruteurFacade.find(recruteur.getId());
        listeOffres=recruteur.getListeOffres();  
        
        listeLangueEnum=new ArrayList<>(Arrays.asList(LangueEnum.values()));
        listeCompetences=new ArrayList<>(competenceFacade.findAll());
        typeContratEnum=TypeContratEnum.values();
        listeSecteursActivite=new ArrayList(secteurActiviteFacade.findAll());
        niveauEtudeEnum=NiveauEtudeEnum.values();
        
    }catch(Exception e){
    }    
            

    }
    
    public String modifierOffre(){
        String url=null;
            try{
                selectedOffre.setDateModification(new Date());
                //verify if active has changed, if a changed has occured, we modify dateActivation or dateDesactivation depends on the situation
                boolean activeFromDatabase=offreFacade.find(selectedOffre.getId()).getActive();
                if(activeFromDatabase==true && selectedOffre.getActive()==false)
                    selectedOffre.setDateDesactivation(new Date());
                
                if(activeFromDatabase==false && selectedOffre.getActive()==true)
                    selectedOffre.setDateActivation(new Date());
                
                
                offreFacade.edit(selectedOffre);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modifications enregistrées"));
                url ="/secured/espace-recruteur/mes-offres";
                
            }catch(Exception e){
                System.out.println(e);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite",""));
            }
        
        return url;
    }
    
    //update selectOneMenus to not contain what the lists already contains
    public void updateMenus(){
        System.out.println("selected offre "+selectedOffre);
        if(selectedOffre!=null){
        //update menu of langues  
        if(selectedOffre.getListeLangues_offre()!=null )                  
            for(int i=0;i<selectedOffre.getListeLangues_offre().size();i++)
                if(listeLangueEnum.contains(selectedOffre.getListeLangues_offre().get(i).getLangueEnum()))
                    listeLangueEnum.remove(selectedOffre.getListeLangues_offre().get(i).getLangueEnum());
        
        //update menu of competences              
        if(selectedOffre.getListeCompetences_offre()!=null)     
            for(int i=0;i<selectedOffre.getListeCompetences_offre().size();i++)
                if(listeCompetences.contains(selectedOffre.getListeCompetences_offre().get(i).getCompetence()))
                    listeCompetences.remove(selectedOffre.getListeCompetences_offre().get(i).getCompetence());            
            
        
        }
    }
    
    public void addLangue(){               
        Langue_offre langue_offre=new Langue_offre();              
        langue_offre.setLangueEnum(selectedLangue);             
        langue_offre.setOffre(selectedOffre);
        selectedOffre.getListeLangues_offre().add(langue_offre);
        listeLangueEnum.remove(selectedLangue);
    }
    
    public void addCompetence(){
            
        Competence_offre competence_offre=new Competence_offre();
        selectedCompetence=competenceFacade.find(selectedCompetence.getId());
        competence_offre.setCompetence(selectedCompetence);
        competence_offre.setOffre(selectedOffre);
        selectedOffre.getListeCompetences_offre().add(competence_offre);
        listeCompetences.remove(selectedCompetence);
    }    
    
    
    
    
    /* méthode qui calcule le nombre de candidatures pour une offre donnée*/
    public long nbrCandidatures(int offreId){
        return offreFacade.countCandidatures(offreId);
    }

    //googl map
    public void onMarkerDrag(MarkerDragEvent event) {
        marker = event.getMarker();
    }
    
    public void modifierLatLang(){
        mapModel.getMarkers().clear();
        if(selectedOffre!=null){
            System.out.println("slelected modifier "+selectedOffre);
            LatLng coord;
            if(selectedOffre.getLatitude()!=null && selectedOffre.getLongitude()!=null){
                coord = new LatLng(selectedOffre.getLatitude(),selectedOffre.getLongitude() );    
                 marker =new Marker(coord);
                marker.setDraggable(true);
                mapModel.addOverlay(marker);
            }
         
            else{
                coord = new LatLng(36.7525000, 3.0419700);  
                 marker =new Marker(coord);
                marker.setDraggable(true);
                mapModel.addOverlay(marker);
            }


        }
        
    }  
    
    public void handleClose(CloseEvent event) {
        init();
    }
    
    public void choisirPosition(){
        
        if(marker!=null && selectedOffre!=null){
            selectedOffre=offreFacade.find(selectedOffre.getId());
            selectedOffre.setLatitude(marker.getLatlng().getLat());
            selectedOffre.setLongitude(marker.getLatlng().getLng());
            try{
            offreFacade.edit(selectedOffre);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
    
    
    
    public Recruteur getRecruteur() {
        return recruteur;
    }

    public void setRecruteur(Recruteur recruteur) {
        this.recruteur = recruteur;
    }

    public ArrayList<Offre> getListeOffres() {
        return listeOffres;
    }

    public void setListeOffres(ArrayList<Offre> listeOffres) {
        this.listeOffres = listeOffres;
    }

    public Offre getSelectedOffre() {
        return selectedOffre;
    }

    public void setSelectedOffre(Offre selectedOffre) {
        this.selectedOffre = selectedOffre;
    }

    public MapModel getMapModel() {
        return mapModel;
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
