/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.inscription;

import entities.Candidat;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;



import services.CandidatFacade;
import services.MembreFacade;
import utilities.EncryptPassword;

/**
 *
 * @author AmineD
 */

@Named
@RequestScoped
public class CandidatInscriptionControleur implements Serializable{
    
    
    @Inject
    private CandidatFacade candidatFacade;
    @EJB
    private MembreFacade membreFacade;
    
    private Candidat candidat;

    public CandidatInscriptionControleur() {
    }
    
    @PostConstruct
    public void init(){
       candidat=new Candidat();

    }
    
    public void creerCandidat() {
        if(membreFacade.findByEmail(candidat.getEmail())==true)
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_FATAL,"Email déja utilisé",""));        
        else{
            try{
                Date date=new Date();
                candidat.setDateCreation(date);
                candidat.setDtype("Candidat");
                candidat.setMdp(EncryptPassword.encyptMD5(candidat.getMdp()));           

                candidatFacade.create(candidat);

                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Compte crée","Verifier votre adresse email pour la confirmation"));       
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

            }
            catch(Exception e){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_FATAL,"erreur lors de la persistance",""));
            }
        }
    }   
    
  

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }    


    
}
