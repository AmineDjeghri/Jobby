/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.candidature;

import entities.Candidat;
import entities.Candidature;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import services.CandidatFacade;
import services.CandidatureFacade;

/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeCandidatures_CvControleur implements Serializable{
    @EJB
    private CandidatureFacade candidatureFacade;
    
    @EJB
    private CandidatFacade candidatFacade;
    
    private Candidat candidat;
    
    private Candidature selectedCandidature;
    private ArrayList<Candidature> listeCandidatures;
    
    @PostConstruct
    public void init(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        candidat=(Candidat)session.getAttribute("membre");
        try{
        candidat=candidatFacade.find(candidat.getId());
        System.out.println("liste "+candidatureFacade);
        if(candidat.getCvActif()!=null)
        listeCandidatures=candidatureFacade.findCandidaturesByCv(candidat.getCvActif().getId());
                }catch(Exception e){
                    e.printStackTrace();
                }
    }

    public Candidature getSelectedCandidature() {
        return selectedCandidature;
    }

    public void setSelectedCandidature(Candidature selectedCandidature) {
        this.selectedCandidature = selectedCandidature;
    }

    public ArrayList<Candidature> getListeCandidatures() {
        return listeCandidatures;
    }

    public void setListeCandidatures(ArrayList<Candidature> listeCandidatures) {
        this.listeCandidatures = listeCandidatures;
    }
    
    
    
}
