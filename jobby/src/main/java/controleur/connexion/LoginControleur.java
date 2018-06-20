/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.connexion;

import entities.Membre;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.annotation.PreDestroy;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import services.MembreFacade;
import utilities.MySessionCounter;


/**
 *
 * @author AmineD
 */
@Named
@SessionScoped
public class LoginControleur implements Serializable{
    private String email;
    private String mdp;
    private Boolean rememberMe=false;
    @EJB
    private MembreFacade membreFacade;
    private boolean loggedIn=false;
    private Membre membre;
    public LoginControleur() {
    }
     
    public void login(ActionEvent event) throws NoSuchAlgorithmException {
        FacesMessage message = null;
        FacesContext context=FacesContext.getCurrentInstance();
         loggedIn = false;
           membre=membreFacade.loginByUID(email, mdp);

        if(membre!=null) {
            loggedIn = true;
            try {
                message=new FacesMessage("bienvenue"+membre.getNom());
                membre.setDateDerniereConx(new Date());
                context.getExternalContext().getSessionMap().put("membre", membre);
                context.getExternalContext().getSessionMap().put("role", membre.getDtype());
                context.getExternalContext().redirect("secured/home.xhtml");
                
                
                membreFacade.edit(membre);                
                
                
                System.out.println("loggedin"+loggedIn);
                 MySessionCounter.connectedUsers++;
                 System.out.println("online users:  "+MySessionCounter.connectedUsers);
                if(!rememberMe)                 
                context.getExternalContext().setSessionMaxInactiveInterval(120*60); 
                else                              
                context.getExternalContext().setSessionMaxInactiveInterval(-1);
                
                } catch (IOException e){ 
                System.out.println("EXCEPTION"+e);}
        } else {
            loggedIn = false;
            System.out.println("loggedin"+loggedIn);
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur ", "Email ou mot de passe invalide");
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);

        } 
    
    public void logout() {
        
        System.out.println("membre logout"+membre.getMdp());
     	FacesContext context = FacesContext.getCurrentInstance();
     	context.getExternalContext().invalidateSession();
        try {
            context.getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
             System.out.println(e);
            }
    }
    

        public int getNumberConnectedUser(){
        
        return MySessionCounter.connectedUsers;
    }
 
    @PreDestroy()
    public void getInfoUser()
    { 
  
        System.out.println("called release connection");
      
    }        

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }



    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }
    
    

}
