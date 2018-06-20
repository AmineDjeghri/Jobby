/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.administration;

import entities.SecteurActivite;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import services.SecteurActiviteFacade;

/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeSecteursActiviteControleur implements Serializable{
    
    @EJB
    SecteurActiviteFacade secteurActiviteFacade;
    
    ArrayList<SecteurActivite> listeSecteurs=new ArrayList<>();
    
    SecteurActivite selectedSecteurActivite;
    SecteurActivite nouveauSecteurActivite;
    
    
    @PostConstruct
    public void init(){
        nouveauSecteurActivite=new SecteurActivite();
        
        System.out.println("secteur "+nouveauSecteurActivite);
        try{
            listeSecteurs=new ArrayList<>(secteurActiviteFacade.findAll());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public String creerSecteurActivite(){
        String url=null;
        System.out.println("liste" +listeSecteurs);
        System.out.println("new secteur "+nouveauSecteurActivite);
        
        if(nouveauSecteurActivite!=null){
            try{
                secteurActiviteFacade.create(nouveauSecteurActivite);
                nouveauSecteurActivite=new SecteurActivite();
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Secteur d'activité crée",""));
                 url="/secured/espace-admin/liste-secteurs-activite.xhtml";   
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return url;
    }
    
    public void selectRow(){
        
        System.out.println("selceted "+selectedSecteurActivite);
    }
    
    public void onRowEdit(RowEditEvent event) {
        try{
            SecteurActivite sa=(SecteurActivite) event.getObject();
            secteurActiviteFacade.edit(sa);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        FacesMessage msg = new FacesMessage("Nom du secteur modifié");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Modification annulée");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    

    public ArrayList<SecteurActivite> getListeSecteurs() {
        return listeSecteurs;
    }

    public void setListeSecteurs(ArrayList<SecteurActivite> listeSecteurs) {
        this.listeSecteurs = listeSecteurs;
    }

    public SecteurActivite getSelectedSecteurActivite() {
        return selectedSecteurActivite;
    }

    public void setSelectedSecteurActivite(SecteurActivite selectedSecteurActivite) {
        this.selectedSecteurActivite = selectedSecteurActivite;
    }

    public SecteurActivite getNouveauSecteurActivite() {
        return nouveauSecteurActivite;
    }

    public void setNouveauSecteurActivite(SecteurActivite nouveauSecteurActivite) {
        this.nouveauSecteurActivite = nouveauSecteurActivite;
    }
    
    
    
}
