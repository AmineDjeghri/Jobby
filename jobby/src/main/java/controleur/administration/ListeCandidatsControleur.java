/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.administration;

import entities.Candidat;
import enumerations.SituationFamilialeEnum;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import services.CandidatFacade;

/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeCandidatsControleur implements Serializable{
    @EJB
    private CandidatFacade candidatFacade;
    
    private Candidat selectedCandidat;
    private ArrayList<Candidat> listeCandidats=new ArrayList<>();
    private SituationFamilialeEnum[] situationFamilialeEnums;
    
    @PostConstruct
    public void init(){
        
        try{
            listeCandidats=new ArrayList<>(candidatFacade.findAll());
            situationFamilialeEnums=SituationFamilialeEnum.values();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        try{
            Candidat r=(Candidat)event.getObject();
           
            candidatFacade.edit(r);
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

    public ArrayList<Candidat> getListeCandidats() {
        return listeCandidats;
    }

    public void setListeCandidats(ArrayList<Candidat> listeCandidats) {
        this.listeCandidats = listeCandidats;
    }
    public SituationFamilialeEnum[] getSituationFamilialeEnums() {
        return situationFamilialeEnums;
    }

    public void setSituationFamilialeEnums(SituationFamilialeEnum[] situationFamilialeEnums) {
        this.situationFamilialeEnums = situationFamilialeEnums;
    }


    
}
