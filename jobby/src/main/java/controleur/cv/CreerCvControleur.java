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
public class CreerCvControleur implements Serializable{
    @EJB 
    private CvFacade cvFacade;
    @EJB 
    private CandidatFacade candidatFacade;
    @EJB
    private SecteurActiviteFacade secteurActiviteFacade;
    @EJB
    private CompetenceFacade competenceFacade;
    
    private Cv cv;
    private Candidat candidat;
    
    private Formation selectedFormation;
    private Experience selectedExperience;
    private Lien selectedLien;
    private Publication selectedPublication; 
    
    private ArrayList<LangueEnum> langueEnum=new ArrayList<>();
    private LangueEnum selectedLangue;
    
    private ArrayList<Competence> listeCompetences;
    private Competence selectedCompetence;
    
    private ArrayList<SecteurActivite> listeSecteursActivite;
    
    private NiveauPosteEnum niveauPosteEnum[];
    
    private NiveauEtudeEnum niveauEtudeEnum[];
    
    private SituationActuelleEnum situationActuelleEnum[];

    
    @PostConstruct
    public void init(){
      
        
        langueEnum=new ArrayList<>(Arrays.asList(LangueEnum.values()));
        
        niveauPosteEnum =NiveauPosteEnum.values();
        niveauEtudeEnum=NiveauEtudeEnum.values();
        situationActuelleEnum=SituationActuelleEnum.values();
        
        selectedFormation=new Formation();
        selectedExperience=new Experience();
        selectedLien=new Lien();
        selectedPublication =new Publication();
        
        cv=new Cv();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try{
        candidat=(Candidat)session.getAttribute("membre");
        candidat=candidatFacade.find(candidat.getId());
        cv.setCandidat(candidat);
        
        //avoir les secteurs d'activité et les competences
        listeSecteursActivite=new ArrayList<>(secteurActiviteFacade.findAll());
        listeCompetences=new ArrayList<>(competenceFacade.findAll());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String creerCv(){
        String url=null;
        try{  
         cvFacade.create(cv);
        candidat.getListeCv().add(cv);
        
        url="/secured/espace-candidat/mes-cvs";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Création du cv avec succés"));
            }catch(Exception e) {
                System.out.println(e);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite",""));
            }
     return url;
        }
       
    public void addFormation(){
        cv.getListeFormations().add(selectedFormation);
        selectedFormation.setCv(cv);
        selectedFormation=new Formation();
    }
    
    public void addExperience(){
    cv.getListeExperiences().add(selectedExperience);
    selectedExperience.setCv(cv);
    selectedExperience=new Experience();
    }
    
    
    public void addLangue(){
        Langue_cv langue_cv=new Langue_cv();
        langue_cv.setLangueEnum(selectedLangue);
        langue_cv.setCv(cv);
        cv.getListeLangues_cv().add(langue_cv);
        langueEnum.remove(langue_cv.getLangueEnum());
    }
    public void addCompetence(){
        Competence_cv competence_cv =new Competence_cv();
        selectedCompetence=competenceFacade.find(selectedCompetence.getId());
        competence_cv.setCompetence(selectedCompetence);
        competence_cv.setCv(cv);
        cv.getListeCompetences_cv().add(competence_cv);
        listeCompetences.remove(competence_cv.getCompetence());
    }
    
    public void addLien(){
        cv.getListeLiens().add(selectedLien);
        selectedLien.setCv(cv);
        selectedLien=new Lien();
    }  
    
    public void addPublication(){
        cv.getListePublications().add(selectedPublication);
        selectedPublication.setCv(cv);
        selectedPublication=new Publication();
    } 

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    } 

    public ArrayList<LangueEnum> getLangueEnum() {
        return langueEnum;
    }

    public void setLangueEnum(ArrayList<LangueEnum> langueEnum) {
        this.langueEnum = langueEnum;
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
 
    
    
}
