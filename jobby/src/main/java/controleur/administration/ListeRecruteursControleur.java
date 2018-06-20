/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.administration;

import entities.Recruteur;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import services.RecruteurFacade;

/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeRecruteursControleur implements Serializable{
    
    @EJB
    private RecruteurFacade recruteurFacade;
    
    private Recruteur selectedRecruteur;
    private ArrayList<Recruteur> listeRecruteurs=new ArrayList<>();
    
    @PostConstruct
    public void init(){
        
        try{
            listeRecruteurs=new ArrayList<>(recruteurFacade.findAll());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        try{
            Recruteur r=(Recruteur)event.getObject();
           
            recruteurFacade.edit(r);
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

    public Recruteur getSelectedRecruteur() {
        return selectedRecruteur;
    }

    public void setSelectedRecruteur(Recruteur selectedRecruteur) {
        this.selectedRecruteur = selectedRecruteur;
    }

    public ArrayList<Recruteur> getListeRecruteurs() {
        return listeRecruteurs;
    }

    public void setListeRecruteurs(ArrayList<Recruteur> listeRecruteurs) {
        this.listeRecruteurs = listeRecruteurs;
    }
    
    
    
    
}
