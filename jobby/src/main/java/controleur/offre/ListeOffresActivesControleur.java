/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.offre;

import entities.Candidat;
import entities.Candidature;
import entities.CandidaturePK;
import entities.Membre;
import entities.Offre;
import entities.SecteurActivite;
import enumerations.NiveauEtudeEnum;
import enumerations.NiveauPosteEnum;
import enumerations.TypeContratEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import services.CandidatFacade;
import services.CandidatureFacade;
import services.OffreFacade;
import services.SecteurActiviteFacade;


/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class ListeOffresActivesControleur implements Serializable{
    
    @EJB
    private OffreFacade offreFacade;
    @EJB
    private CandidatFacade candidatFacade;
    @EJB
    private CandidatureFacade candidatureFacade;
    @EJB
    private SecteurActiviteFacade secteurActiviteFacade;
    
    
    private ArrayList<Offre> listeOffresActives;
    private Offre selectedOffre;
    private Membre membre;
    private Candidat candidat;
    private boolean dejaCandidat=false;
    
    private ArrayList<SecteurActivite>listeSecteurActivite;
    private NiveauEtudeEnum niveauEtudeEnum [];
    private NiveauPosteEnum niveauPosteEnums [];
    private TypeContratEnum typeContratEnums [];
    
    
    private SecteurActivite filteredSecteurActivite;
    private NiveauEtudeEnum filtredNiveauEtude;
    private NiveauPosteEnum filterNiveauPoste;
    private TypeContratEnum filterTypeContrat;
    private Integer filterSalaireMin;
    private Integer filterSalaireMax;
    private Integer filterAnneeExpMin;
    private Integer filterAnneeExpMax;
    private String keyWord;
    
    
    //google map
      private final static MapModel simpleModel=new DefaultMapModel(); 
    
    @PostConstruct
    public void init(){
        
        System.out.println("filtred secteur activite "+filteredSecteurActivite);
        
        
        //for filter 
        try{
            listeSecteurActivite=new ArrayList<>(secteurActiviteFacade.findAll());
            niveauEtudeEnum=NiveauEtudeEnum.values();
            niveauPosteEnums=NiveauPosteEnum.values();
            typeContratEnums=TypeContratEnum.values();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        //clearing all the markers then putting the new ones with getOffersMarkers
        simpleModel.getMarkers().clear();
        
        selectedOffre=new Offre();
        listeOffresActives=offreFacade.findActiveOffers();
        Collections.reverse(listeOffresActives);
        getOffersMakers();
        System.out.println("init 1");
        //check if le membre est connecté
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        membre=(Membre)session.getAttribute("membre");
        System.out.println("membre "+membre);
        if(membre!=null && membre.hasType("Candidat"))
            candidat=candidatFacade.find(membre.getId());
        
    }
    
    public void filtrer(){
        listeOffresActives=offreFacade.filterOffersByParams(filteredSecteurActivite,filtredNiveauEtude,filterNiveauPoste,filterTypeContrat,filterSalaireMin,filterSalaireMax,filterAnneeExpMin,filterAnneeExpMax);
         Collections.reverse(listeOffresActives);
        simpleModel.getMarkers().clear();
        getOffersMakers();
        
    }
    
    public void searchByKeyWord(){
        System.out.println("keyword "+keyWord);
        if (keyWord != null && !"".equals(keyWord.trim()) && !"null".equals(keyWord)){
           listeOffresActives= offreFacade.searchByKeyWord(keyWord);
            Collections.reverse(listeOffresActives);
        }
        else{
            listeOffresActives=offreFacade.findActiveOffers();
             Collections.reverse(listeOffresActives);
        }
        simpleModel.getMarkers().clear();
        getOffersMakers();
    }
    
    public long nbreOffresActives(){
        return offreFacade.countActiveOffers();
    }
    
    
    public void checkCandidature(){
      System.out.println("deja candidat "+dejaCandidat); 

        if(selectedOffre!=null && candidat!=null )
            if(candidat.getCvActif()!=null){
                Candidature candidatureTmp=new Candidature();             
                candidatureTmp.setId(new CandidaturePK(candidat.getCvActif().getId(),selectedOffre.getId()));
                candidatureTmp=candidatureFacade.find(candidatureTmp.getId());
                
                
                if(candidatureTmp!=null)
                    dejaCandidat=true;
                else dejaCandidat=false;
                        }
        System.out.println("deja candidat 123456"+dejaCandidat);
    }
    
    public void postuler(){
        System.out.println("deja candidat 123456"+dejaCandidat);
       if(!dejaCandidat && candidat!=null)
            if(candidat.getCvActif()==null)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vous ne pouvez pas postuler sans avoir un CV actif"));
            else{
            System.out.println("postuler candidature 0");
           Candidature candidature=new Candidature();
           candidature.setId(new CandidaturePK(candidat.getCvActif().getId(),selectedOffre.getId()));
           candidature.setCv(candidat.getCvActif());
           candidature.setDateCandidature(new Date());
           candidature.setOffre(selectedOffre);
           System.out.println("postuler candidature 1");
           try{
          candidatureFacade.create(candidature);
           }catch(Exception e){
               System.out.println(e);
           }
       }     
    }
    
    public void enlever(){
                System.out.println("deja candidat 123456"+dejaCandidat);
       if(dejaCandidat && candidat!=null && selectedOffre!=null)
             if(candidat.getCvActif()!=null) {
            System.out.println("selec "+selectedOffre);
            System.out.println("candiat cv "+selectedOffre);
            System.out.println("selec "+selectedOffre);
                Candidature candidatureTmp=new Candidature();             
                candidatureTmp.setId(new CandidaturePK(candidat.getCvActif().getId(),selectedOffre.getId()));
                candidatureTmp=candidatureFacade.find(candidatureTmp.getId());
           System.out.println("postuler candidature 1");
           if(candidatureTmp!=null)
           try{
          candidatureFacade.remove(candidatureTmp);
           }catch(Exception e){
               System.out.println(e);
           }
        }
    }
           
      
           //google maps
     public MapModel getOffersMakers(){
              for (Offre offre:listeOffresActives) {
                  if(offre.getLatitude()!=null && offre.getLongitude()!=null)
        simpleModel.addOverlay(new Marker(new LatLng(offre.getLatitude(), offre.getLongitude()),offre.getTitre(),offre));
                  }
         return simpleModel;
     }      
     

    public void onMarkerSelect(OverlaySelectEvent event) {
        Marker marker = (Marker) event.getOverlay();
        System.out.println(marker);
    
        try{ selectedOffre=(Offre)marker.getData();
            System.out.println(selectedOffre);
            checkCandidature();
          
        }catch(Exception e){
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vous ne pouvez pas postuler sans avoir un CV actif"));
}

 
    
    public ArrayList<Offre> getListeOffresActives() {
        return listeOffresActives;
    }

    public void setListeOffresActives(ArrayList<Offre> listeOffresActives) {
        this.listeOffresActives = listeOffresActives;
    }

    public Offre getSelectedOffre() {
        return selectedOffre;
    }

    public void setSelectedOffre(Offre selectedOffre) {
        this.selectedOffre = selectedOffre;
    }

    public boolean isDejaCandidat() {
        return dejaCandidat;
    }

    public void setDejaCandidat(boolean dejaCandidat) {
        this.dejaCandidat = dejaCandidat;
    }

    public ArrayList<SecteurActivite> getListeSecteurActivite() {
        return listeSecteurActivite;
    }

    public void setListeSecteurActivite(ArrayList<SecteurActivite> listeSecteurActivite) {
        this.listeSecteurActivite = listeSecteurActivite;
    }

    public SecteurActivite getFilteredSecteurActivite() {
        return filteredSecteurActivite;
    }

    public void setFilteredSecteurActivite(SecteurActivite filteredSecteurActivite) {
        this.filteredSecteurActivite = filteredSecteurActivite;
    }

    public NiveauEtudeEnum[] getNiveauEtudeEnum() {
        return niveauEtudeEnum;
    }

    public void setNiveauEtudeEnum(NiveauEtudeEnum[] niveauEtudeEnum) {
        this.niveauEtudeEnum = niveauEtudeEnum;
    }

    public NiveauEtudeEnum getFiltredNiveauEtude() {
        return filtredNiveauEtude;
    }

    public void setFiltredNiveauEtude(NiveauEtudeEnum filtredNiveauEtude) {
        this.filtredNiveauEtude = filtredNiveauEtude;
    }

    public NiveauPosteEnum[] getNiveauPosteEnums() {
        return niveauPosteEnums;
    }

    public void setNiveauPosteEnums(NiveauPosteEnum[] niveauPosteEnums) {
        this.niveauPosteEnums = niveauPosteEnums;
    }

    public NiveauPosteEnum getFilterNiveauPoste() {
        return filterNiveauPoste;
    }

    public void setFilterNiveauPoste(NiveauPosteEnum filterNiveauPoste) {
        this.filterNiveauPoste = filterNiveauPoste;
    }

    public TypeContratEnum[] getTypeContratEnums() {
        return typeContratEnums;
    }

    public void setTypeContratEnums(TypeContratEnum[] typeContratEnums) {
        this.typeContratEnums = typeContratEnums;
    }

    public TypeContratEnum getFilterTypeContrat() {
        return filterTypeContrat;
    }

    public void setFilterTypeContrat(TypeContratEnum filterTypeContrat) {
        this.filterTypeContrat = filterTypeContrat;
    }

    public Integer getFilterSalaireMin() {
        return filterSalaireMin;
    }

    public void setFilterSalaireMin(Integer filterSalaireMin) {
        this.filterSalaireMin = filterSalaireMin;
    }

    public Integer getFilterSalaireMax() {
        return filterSalaireMax;
    }

    public void setFilterSalaireMax(Integer filterSalaireMax) {
        this.filterSalaireMax = filterSalaireMax;
    }

    public Integer getFilterAnneeExpMin() {
        return filterAnneeExpMin;
    }

    public void setFilterAnneeExpMin(Integer filterAnneeExpMin) {
        this.filterAnneeExpMin = filterAnneeExpMin;
    }

    public Integer getFilterAnneeExpMax() {
        return filterAnneeExpMax;
    }

    public void setFilterAnneeExpMax(Integer filterAnneeExpMax) {
        this.filterAnneeExpMax = filterAnneeExpMax;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }




    
    
    
    
}
