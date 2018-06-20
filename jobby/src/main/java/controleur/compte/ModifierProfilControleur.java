/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.compte;



import entities.Admin;
import entities.Candidat;
import entities.Membre;
import entities.Recruteur;
import enumerations.SituationFamilialeEnum;



import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import services.AdminFacade;
import services.CandidatFacade;
import services.MembreFacade;
import services.RecruteurFacade;
import utilities.EncryptPassword;

@Named
@RequestScoped
public class ModifierProfilControleur implements Serializable{

@EJB
private MembreFacade membreFacade;
@EJB
private CandidatFacade candidatFacade;
@EJB 
private RecruteurFacade recruteurFacade;
@EJB 
private AdminFacade adminFacade;

private String uploadPath="C:\\Users\\AmineD\\Documents\\NetBeansProjects\\jobbyUploads";
private Membre membre;
private Admin admin;
private Candidat candidat;
private Recruteur recruteur;
private UploadedFile photoDeProfile;

private String mdp="";

private SituationFamilialeEnum situationFamilialeEnums [];

    @PostConstruct
    public void init(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try{
            
            situationFamilialeEnums=SituationFamilialeEnum.values();
            
            membre=(Membre)session.getAttribute("membre");
            
//            uploadPath=uploadPath+"\\"+membre.getId();
            
            membre=membreFacade.find(membre.getId());
            System.out.println("membre modifier "+membre.getNom());
            if(membre.hasType("Candidat"))
            { candidat=(Candidat)session.getAttribute("membre");
                System.out.println("candidat: "+candidat.getAdresseCandidat());
            }
            if(membre.hasType("Recruteur"))
                recruteur=(Recruteur)session.getAttribute("membre");
            if(membre.hasType("Admin"))
                admin=(Admin)session.getAttribute("membre");            
        }
        catch(Exception e){
            System.out.println("EXCEPTION "+e);
        }
    }
    
    public void modifierMembre(){
        System.out.println("membre modifier compte "+membre);
        try{
            if(! mdp.equals("") && mdp !=null && !mdp.trim().equals("")){
                System.out.println("membre modifier compte "+membre);
                mdp=EncryptPassword.encyptMD5(mdp);
                membre.setMdp(mdp);
            }
            membreFacade.edit(membre);
            FacesContext context=FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("membre", membre);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modifications effectuées"));
        }
        catch(Exception e){
            System.out.println("exception"+e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite",""));
        
        }
        mdp="";
    }
    
    public void modifierCandidat(){
        try{
            
            candidatFacade.edit(candidat);
            System.out.println("membre compte "+candidat);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modifications effectuées"));
        }
        catch(Exception e){
            System.out.println("exception"+e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite",""));
        }
    }
    public void modifierRecruteur(){
        try{
            recruteurFacade.edit(recruteur);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modifications effectuées"));
        }
        catch(Exception e){
            System.out.println("exception"+e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite",""));
        }
    }
    public void modifierAdmin(){
        try{
            adminFacade.edit(admin);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modifications effectuées"));
        }
        catch(Exception e){
            System.out.println("exception"+e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite",""));
        }
    }



     public void handleFileUpload(FileUploadEvent event) {
         if(membre!=null)
            photoDeProfile=event.getFile();
            String fileName = photoDeProfile.getFileName();
            String[] typeTmp=fileName.split("\\.(?=[^\\.]+$)");
            String type = typeTmp[1];
            fileName = membre.getId()+"-pp."+type;
            Path path=Paths.get(uploadPath+"\\"+fileName);
                  
                   
            try{    
                
                
                    Files.copy(photoDeProfile.getInputstream(), path,StandardCopyOption.REPLACE_EXISTING);
                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Photo de profile mise à jour"));
            }catch(Exception e){
                 System.out.println(e);
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Photo de profile non mise à jour","un problème est survenu"));
            }
            
    }     
    
    

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Recruteur getRecruteur() {
        return recruteur;
    }

    public void setRecruteur(Recruteur recruteur) {
        this.recruteur = recruteur;
    }

    public UploadedFile getPhotoDeProfile() {
        return photoDeProfile;
    }

    public void setPhotoDeProfile(UploadedFile photoDeProfile) {
        this.photoDeProfile = photoDeProfile;
    } 

    public SituationFamilialeEnum[] getSituationFamilialeEnums() {
        return situationFamilialeEnums;
    }

    public void setSituationFamilialeEnums(SituationFamilialeEnum[] situationFamilialeEnums) {
        this.situationFamilialeEnums = situationFamilialeEnums;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    
    
}
