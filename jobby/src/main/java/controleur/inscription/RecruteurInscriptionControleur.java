/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.inscription;


import entities.Recruteur;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import services.MembreFacade;

import services.RecruteurFacade;
import utilities.EncryptPassword;

/**
 *
 * @author AmineD
 */
@Named
@RequestScoped
public class RecruteurInscriptionControleur implements Serializable{
    

    
    @Inject
    private RecruteurFacade recruteurFacade;
    @EJB
    private MembreFacade membreFacade;
    
    private Recruteur recruteur;

    public RecruteurInscriptionControleur() {
    }
    
    @PostConstruct
    public void init(){
       recruteur =new Recruteur();

    }
    
    public void creerRecruteur() {
        if(membreFacade.findByEmail(recruteur.getEmail())==true)
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_FATAL,"Email déja utilisé",""));        
        else{
            try{
                Date date=new Date();
                recruteur.setDateCreation(date);
                recruteur.setDtype("Recruteur");
                recruteur.setMdp(EncryptPassword.encyptMD5(recruteur.getMdp())); 
                recruteurFacade.create(recruteur);

                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Compte crée","Verifier votre adresse email pour la confirmation"));   
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

            }
            catch(Exception e){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_FATAL,"erreur lors de la persistance",""));
            }
        }    
    }   

    public Recruteur getRecruteur() {
        return recruteur;
    }

    public void setRecruteur(Recruteur recruteur) {
        this.recruteur = recruteur;
    }
    
  


    
}
