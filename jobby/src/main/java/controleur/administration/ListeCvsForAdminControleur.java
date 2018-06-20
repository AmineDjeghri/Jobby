/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.administration;

import entities.Cv;
import entities.SecteurActivite;
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
import org.primefaces.event.RowEditEvent;
import services.CvFacade;
import services.SecteurActiviteFacade;

/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeCvsForAdminControleur implements Serializable{
    
     
    @EJB
    private CvFacade cvFacade;
    @EJB
    private SecteurActiviteFacade secteurActiviteFacade;
    private Cv selectedCv;
    
    private ArrayList<SecteurActivite> listeSecteurs=new ArrayList<>();
    private ArrayList<NiveauEtudeEnum> listeNiveauEtude=new ArrayList<>();
    private ArrayList<NiveauPosteEnum> listeNiveauPoste=new ArrayList<>();
    private ArrayList<SituationActuelleEnum> listeSituationActuelle=new ArrayList<>();
    
    private ArrayList<Cv> listeCvs=new ArrayList<>();
    
    @PostConstruct
    public void init(){
        
        try{
            listeCvs=new ArrayList<>(cvFacade.findAll());
            listeSecteurs=new ArrayList<>(secteurActiviteFacade.findAll());
            listeNiveauEtude=new ArrayList<>(Arrays.asList(NiveauEtudeEnum.values()));
            listeNiveauPoste=new ArrayList<>(Arrays.asList(NiveauPosteEnum.values()));
            listeSituationActuelle=new ArrayList<>(Arrays.asList(SituationActuelleEnum.values()));
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        try{
            Cv cv=(Cv)event.getObject();
            cvFacade.edit(cv);
         FacesMessage msg = new FacesMessage("Enregistrement effectué");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        }catch(Exception e){
               e.printStackTrace();
            }

    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Modification annulée");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Cv getSelectedCv() {
        return selectedCv;
    }

    public void setSelectedCv(Cv selectedCv) {
        this.selectedCv = selectedCv;
    }

    public ArrayList<Cv> getListeCvs() {
        return listeCvs;
    }

    public void setListeCvs(ArrayList<Cv> listeCvs) {
        this.listeCvs = listeCvs;
    }

    public ArrayList<SecteurActivite> getListeSecteurs() {
        return listeSecteurs;
    }

    public void setListeSecteurs(ArrayList<SecteurActivite> listeSecteurs) {
        this.listeSecteurs = listeSecteurs;
    }

    public ArrayList<NiveauEtudeEnum> getListeNiveauEtude() {
        return listeNiveauEtude;
    }

    public void setListeNiveauEtude(ArrayList<NiveauEtudeEnum> listeNiveauEtude) {
        this.listeNiveauEtude = listeNiveauEtude;
    }

    public ArrayList<NiveauPosteEnum> getListeNiveauPoste() {
        return listeNiveauPoste;
    }

    public void setListeNiveauPoste(ArrayList<NiveauPosteEnum> listeNiveauPoste) {
        this.listeNiveauPoste = listeNiveauPoste;
    }

    public ArrayList<SituationActuelleEnum> getListeSituationActuelle() {
        return listeSituationActuelle;
    }

    public void setListeSituationActuelle(ArrayList<SituationActuelleEnum> listeSituationActuelle) {
        this.listeSituationActuelle = listeSituationActuelle;
    }
    
 
}
