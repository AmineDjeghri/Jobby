/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.administration;

import entities.Offre;
import entities.SecteurActivite;
import enumerations.NiveauEtudeEnum;
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
import services.OffreFacade;
import services.SecteurActiviteFacade;

/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeOffresForAdminControleur implements Serializable{
    
    @EJB
    private OffreFacade offreFacade;
    @EJB
    private SecteurActiviteFacade secteurActiviteFacade;
    private Offre selectedOffre;
    
    private ArrayList<SecteurActivite> listeSecteurs=new ArrayList<>();
    private ArrayList<NiveauEtudeEnum> listeNiveauEtude=new ArrayList<>();
    private ArrayList<Offre> listeOffres=new ArrayList<>();
    
    @PostConstruct
    public void init(){
        
        try{
            listeOffres=new ArrayList<>(offreFacade.findAll());
            listeSecteurs=new ArrayList<>(secteurActiviteFacade.findAll());
            listeNiveauEtude=new ArrayList<>(Arrays.asList(NiveauEtudeEnum.values()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        try{
            Offre o=(Offre)event.getObject();
           
            offreFacade.edit(o);
            o=offreFacade.find(o.getId());
            System.out.println("id "+o.getId()+" nom "+o.getSecteurActivite());
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

    public Offre getSelectedOffre() {
        return selectedOffre;
    }

    public void setSelectedOffre(Offre selectedOffre) {
        this.selectedOffre = selectedOffre;
    }

    public ArrayList<Offre> getListeOffres() {
        return listeOffres;
    }

    public void setListeOffres(ArrayList<Offre> listeOffres) {
        this.listeOffres = listeOffres;
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

    

    
}
