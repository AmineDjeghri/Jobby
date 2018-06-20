/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.membre;

import entities.Membre;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import services.MembreFacade;

@Named
@ViewScoped
public class ListeMembreControleur implements Serializable{
    @EJB
    private MembreFacade membreFacade;
    
    private List<Membre> listeMembre;
    private Membre selectedMembre;
    
    @PostConstruct
    public void init(){
        System.out.println(" init LISTEFMembreControler ");
         listeMembre= membreFacade.findAll();
        
        if (listeMembre == null){
            listeMembre= new ArrayList<>();
            System.out.println("LISTE DES MEMBRES VIDE");
        }
    }

    public List<Membre> getListeMembre() {
        return listeMembre;
    }

    public void setListeMembre(List<Membre> listeMembre) {
        this.listeMembre = listeMembre;
    }

    public Membre getSelectedMembre() {
        return selectedMembre;
    }

    public void setSelectedMembre(Membre selectedMembre) {
        this.selectedMembre = selectedMembre;
    }
    
       public void onRowEdit(RowEditEvent event) {
           try{
               selectedMembre=(Membre)event.getObject();
               System.out.println("selected dat"+ selectedMembre.getDateCreation());
               membreFacade.edit(selectedMembre);
           }catch(Exception e){
               e.printStackTrace();
           }
        FacesMessage msg = new FacesMessage("Enregistrement effectué");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
