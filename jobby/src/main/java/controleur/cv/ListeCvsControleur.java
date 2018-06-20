/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.cv;

import entities.Candidat;
import entities.Competence;
import entities.Competence_cv;
import entities.Cv;
import entities.Experience;
import entities.Formation;
import entities.Langue_cv;
import entities.Lien;
import entities.Publication;
import entities.SecteurActivite;
import enumerations.LangueEnum;
import enumerations.NiveauEtudeEnum;
import enumerations.NiveauPosteEnum;
import enumerations.SituationActuelleEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CloseEvent;
import services.CandidatFacade;
import services.CompetenceFacade;
import services.CvFacade;
import services.SecteurActiviteFacade;

/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeCvsControleur implements Serializable{
  @EJB
    private CandidatFacade candidatFacade;
    @EJB
    private CvFacade cvFacade;
    @EJB
    private SecteurActiviteFacade secteurActiviteFacade;
    @EJB
    private CompetenceFacade competenceFacade;
 
    private ArrayList<Cv> listeCvs=new ArrayList<>();
    private Cv selectedCv;
    private Candidat candidat;
    
    //choisir cv actif depuis le radio pour le mettre dans candidat
    private Cv cvActif;
    
    private Formation selectedFormation;
    private Experience selectedExperience;
    private Lien selectedLien;
    private Publication selectedPublication; 
    
    private ArrayList<LangueEnum> listeLangueEnums =new ArrayList<>();
    private LangueEnum selectedLangue;
    
    private ArrayList<Competence> listeCompetences;
    private Competence selectedCompetence;
    
    private ArrayList<SecteurActivite> listeSecteursActivite;
    
    private NiveauPosteEnum niveauPosteEnum[];
    
    private NiveauEtudeEnum niveauEtudeEnum[];
    
    private SituationActuelleEnum situationActuelleEnum[];
    
    @PostConstruct
    public void init(){
        try{
        selectedCv=new Cv();
            
            
        listeLangueEnums=new ArrayList<>(Arrays.asList(LangueEnum.values()));
        listeSecteursActivite=new ArrayList<>(secteurActiviteFacade.findAll());
        niveauPosteEnum =NiveauPosteEnum.values();
        niveauEtudeEnum=NiveauEtudeEnum.values();
        situationActuelleEnum=SituationActuelleEnum.values();
        listeCompetences=new ArrayList<>(competenceFacade.findAll());
        
        selectedFormation=new Formation();
        selectedExperience=new Experience();
        selectedLien=new Lien();
        selectedPublication =new Publication();
        
        selectedCv=new Cv();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        candidat=(Candidat)session.getAttribute("membre"); 
        candidat=candidatFacade.find(candidat.getId());
        
        if(candidat!=null){
        listeCvs=candidat.getListeCv();
        cvActif=candidat.getCvActif();
        }}
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String modifierCv(){
        
        String url=null;
        if(selectedCv!=null){
            try{
                cvFacade.edit(selectedCv);
                url="/secured/espace-candidat/mes-cvs";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modifications effectuées"));                
            }
            catch(Exception e){
                System.out.println(e);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite",""));                    
                    }
        }
        return url;
    }

    public void updateSelectMenus(){
        System.out.println("selected Cv "+selectedCv);
         if(selectedCv!=null){
        //update menu of langues  
        if(selectedCv.getListeLangues_cv()!=null )                  
            for(int i=0;i<selectedCv.getListeLangues_cv().size();i++)
                if(listeLangueEnums.contains(selectedCv.getListeLangues_cv().get(i).getLangueEnum()))
                    listeLangueEnums.remove(selectedCv.getListeLangues_cv().get(i).getLangueEnum());
        
        //update menu of competences              
        if(selectedCv.getListeCompetences_cv()!=null)     
            for(int i=0;i<selectedCv.getListeCompetences_cv().size();i++)
                if(listeCompetences.contains(selectedCv.getListeCompetences_cv().get(i).getCompetence()))
                    listeCompetences.remove(selectedCv.getListeCompetences_cv().get(i).getCompetence());            
            
        
        }

    }
    
    public void addFormation(){
        selectedCv.getListeFormations().add(selectedFormation);
        selectedFormation.setCv(selectedCv);
        selectedFormation=new Formation();
    }
    
    public void addExperience(){
    selectedCv.getListeExperiences().add(selectedExperience);
    selectedExperience.setCv(selectedCv);
    selectedExperience=new Experience();
    }
    
    
    public void addLangue(){
        Langue_cv langue_cv=new Langue_cv();
        langue_cv.setLangueEnum(selectedLangue);
        langue_cv.setCv(selectedCv);
        selectedCv.getListeLangues_cv().add(langue_cv);
        listeLangueEnums.remove(langue_cv.getLangueEnum());
    }
    public void addCompetence(){
        Competence_cv competence_cv =new Competence_cv();
        competence_cv.setCompetence(selectedCompetence);
        competence_cv.setCv(selectedCv);
        selectedCv.getListeCompetences_cv().add(competence_cv);
        listeCompetences.remove(competence_cv.getCompetence());
    }
    
    public void addLien(){
        selectedCv.getListeLiens().add(selectedLien);
        selectedLien.setCv(selectedCv);
        selectedLien=new Lien();
    }  
    
    public void addPublication(){
        selectedCv.getListePublications().add(selectedPublication);
        selectedPublication.setCv(selectedCv);
        selectedPublication=new Publication();
    }     
    
    

    public String choisirCvActif(){
        if(candidat!=null ){       
            if(cvActif!=null){          
            candidat.setCvActif(cvActif);}
            System.out.println("edit cvActif " +candidat.getCvActif());
            candidatFacade.edit(candidat);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( "Votre Cv actif est ",""));  
            return "/secured/espace-candidat/mes-cvs";
        
        }
        
            return null;
       }
    
    public void handleClose(CloseEvent event) {
        init();
        
    }
    
    
    public ArrayList<Cv> getListeCvs() {
        return listeCvs;
    }

    public void setListeCvs(ArrayList<Cv> listeCvs) {
        this.listeCvs = listeCvs;
    }

    public Cv getSelectedCv() {
        return selectedCv;
    }

    public void setSelectedCv(Cv selectedCv) {
        this.selectedCv = selectedCv;
    }  

    public Cv getCvActif() {
        return cvActif;
    }

    public void setCvActif(Cv cvActif) {
        this.cvActif = cvActif;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Formation getSelectedFormation() {
        return selectedFormation;
    }

    public void setSelectedFormation(Formation selectedFormation) {
        this.selectedFormation = selectedFormation;
    }

    public Experience getSelectedExperience() {
        return selectedExperience;
    }

    public void setSelectedExperience(Experience selectedExperience) {
        this.selectedExperience = selectedExperience;
    }

    public Lien getSelectedLien() {
        return selectedLien;
    }

    public void setSelectedLien(Lien selectedLien) {
        this.selectedLien = selectedLien;
    }

    public Publication getSelectedPublication() {
        return selectedPublication;
    }

    public void setSelectedPublication(Publication selectedPublication) {
        this.selectedPublication = selectedPublication;
    }

    public ArrayList<LangueEnum> getListeLangueEnums() {
        return listeLangueEnums;
    }

    public void setListeLangueEnums(ArrayList<LangueEnum> listeLangueEnums) {
        this.listeLangueEnums = listeLangueEnums;
    }

    public LangueEnum getSelectedLangue() {
        return selectedLangue;
    }

    public void setSelectedLangue(LangueEnum selectedLangue) {
        this.selectedLangue = selectedLangue;
    }

    public ArrayList<Competence> getListeCompetences() {
        return listeCompetences;
    }

    public void setListeCompetences(ArrayList<Competence> listeCompetences) {
        this.listeCompetences = listeCompetences;
    }

    public Competence getSelectedCompetence() {
        return selectedCompetence;
    }

    public void setSelectedCompetence(Competence selectedCompetence) {
        this.selectedCompetence = selectedCompetence;
    }

    public ArrayList<SecteurActivite> getListeSecteursActivite() {
        return listeSecteursActivite;
    }

    public void setListeSecteursActivite(ArrayList<SecteurActivite> listeSecteursActivite) {
        this.listeSecteursActivite = listeSecteursActivite;
    }

    public NiveauPosteEnum[] getNiveauPosteEnum() {
        return niveauPosteEnum;
    }

    public void setNiveauPosteEnum(NiveauPosteEnum[] niveauPosteEnum) {
        this.niveauPosteEnum = niveauPosteEnum;
    }

    public NiveauEtudeEnum[] getNiveauEtudeEnum() {
        return niveauEtudeEnum;
    }

    public void setNiveauEtudeEnum(NiveauEtudeEnum[] niveauEtudeEnum) {
        this.niveauEtudeEnum = niveauEtudeEnum;
    }

    public SituationActuelleEnum[] getSituationActuelleEnum() {
        return situationActuelleEnum;
    }

    public void setSituationActuelleEnum(SituationActuelleEnum[] situationActuelleEnum) {
        this.situationActuelleEnum = situationActuelleEnum;
    }
    
    
}
