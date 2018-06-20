/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.administration;

import entities.Competence;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import services.CompetenceFacade;


/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeCompetencesControleur implements Serializable{
    
    @EJB
    CompetenceFacade competenceFacade;
    
    ArrayList<Competence> listeCompetences=new ArrayList<>();
    
    private Competence selectedCompetence;
    private Competence nouveauCompetence;
    
    
    @PostConstruct
    public void init(){
        nouveauCompetence=new Competence();
        try{
            listeCompetences=new ArrayList<>(competenceFacade.findAll());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public String creerCompetence(){
        String url=null;

        
        if(nouveauCompetence!=null){
            try{
                competenceFacade.create(nouveauCompetence);
                nouveauCompetence=new Competence();
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Competence crée",""));
                 url="/secured/espace-admin/liste-competences.xhtml";   
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return url;
    }
    
    public void selectRow(){
        
        System.out.println("selceted "+selectedCompetence);
    }
    
    public void onRowEdit(RowEditEvent event) {
        try{
            Competence c=(Competence) event.getObject();
            competenceFacade.edit(c);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        FacesMessage msg = new FacesMessage("Competence modifiée");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Modification annulée");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

    public Competence getNouveauCompetence() {
        return nouveauCompetence;
    }

    public void setNouveauCompetence(Competence nouveauCompetence) {
        this.nouveauCompetence = nouveauCompetence;
    }
    
    
    
}
