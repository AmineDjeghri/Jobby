/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.candidature;

import entities.Candidature;
import enumerations.TypeReponseEnum;
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
import services.CandidatureFacade;

/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeCandidaturesControleur implements Serializable{
    
    @EJB
    private CandidatureFacade candidatureFacade;
    private Candidature selectedCandidature;
    
    private ArrayList<Candidature> listeCandidatures=new ArrayList<>();
    
    private ArrayList<TypeReponseEnum> listeTypesReponses=new ArrayList<>();
    
    
    @PostConstruct
    public void init(){
        
        try{
            listeCandidatures=new ArrayList<>(candidatureFacade.findAll());
            listeTypesReponses=new ArrayList<>(Arrays.asList(TypeReponseEnum.values()));
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        try{
            Candidature c=(Candidature)event.getObject();
            candidatureFacade.edit(c);
        }catch(Exception e){
               e.printStackTrace();
            }
        FacesMessage msg = new FacesMessage("Enregistrement effectué");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Modification annulée");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public ArrayList<Candidature> getListeCandidatures() {
        return listeCandidatures;
    }

    public void setListeCandidatures(ArrayList<Candidature> listeCandidatures) {
        this.listeCandidatures = listeCandidatures;
    }

    public Candidature getSelectedCandidature() {
        return selectedCandidature;
    }

    public void setSelectedCandidature(Candidature selectedCandidature) {
        this.selectedCandidature = selectedCandidature;
    }

    public ArrayList<TypeReponseEnum> getListeTypesReponses() {
        return listeTypesReponses;
    }

    public void setListeTypesReponses(ArrayList<TypeReponseEnum> listeTypesReponses) {
        this.listeTypesReponses = listeTypesReponses;
    }
    
}
