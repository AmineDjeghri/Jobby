/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Offre;
import entities.SecteurActivite;
import enumerations.NiveauEtudeEnum;
import enumerations.NiveauPosteEnum;
import enumerations.TypeContratEnum;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AmineD
 */
@Stateless
public class OffreFacade extends AbstractFacade<Offre> {

    @PersistenceContext(unitName = "com.mycompany_jobby_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OffreFacade() {
        super(Offre.class);
    }
    
    
    public ArrayList<Offre> findActiveOffers(){
        ArrayList<Offre> activeOffers=null;
        Query q=em.createNamedQuery("Offre.findActiveOffers");
        
        try{
        activeOffers=new ArrayList<>(q.getResultList());
        }catch(Exception e){
            System.out.println(e);
        }
        return activeOffers;
    }
    
    public long countActiveOffers(){
        
        Query q=em.createNamedQuery("Offre.countActiveOffers");  
        try{
        return (long)q.getSingleResult();
        }catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public long countCandidatures(int offreId){
        long nbr=0;
        Query q=em.createNamedQuery("Candidature.countByOffreId");
        q.setParameter("offreId", offreId);
        try{
            System.out.println("nbre "+nbr);
            System.out.println("count "+ offreId+" nbr"+q.getSingleResult());
            nbr=(long)q.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return nbr;
    }
    public long countTodayOffers(){
        long nbr=0;
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        
        
        Date date1=cal.getTime();
        
        Date date2=new Date();   
        Query q=em.createNamedQuery("Offre.countOffersByMargeDateCreation").setParameter("dateCreation1", date1).setParameter("dateCreation2", date2);
        
        try{
            
            nbr=(long)q.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return nbr;
    }
    public long countOffersByMargeDateCreation(Date startDate,Date endDate){
        long nbr=0;
        
          
        Query q=em.createNamedQuery("Offre.countOffersByMargeDateCreation").setParameter("dateCreation1", startDate).setParameter("dateCreation2", endDate);
        
        try{
            
            nbr=(long)q.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return nbr;
    } 
    
    public long countOffersBySecteur(SecteurActivite secteurActivite){
        
        long nbr=0;    
            Query q=em.createNamedQuery("Offre.countOffreBySecteur").setParameter("secteurActivite", secteurActivite);

            try{

                nbr=(long)q.getSingleResult();
            }catch(Exception e){
                e.printStackTrace();
            }

            return nbr;
    }    
    
    public ArrayList<Offre> filterOffersByParams(SecteurActivite secteurActivite, NiveauEtudeEnum niveauEtudeEnum,NiveauPosteEnum niveauPosteEnum,TypeContratEnum typeContratEnum,Integer filterSalaireMin,Integer filterSalaireMax,Integer filterAnneeExpMin,Integer filterAnneeExpMax) {
        
        ArrayList<Offre> filtredOffersList=new ArrayList<>();
        StringBuilder queryStringBuilder = new StringBuilder();
        queryStringBuilder.append("SELECT o FROM Offre o where o.active = true ");
        
        
        if (secteurActivite != null ) {
        queryStringBuilder.append(" AND o.secteurActivite = :secteurActivite ");
        }
        if (niveauEtudeEnum != null ) {
        queryStringBuilder.append(" AND o.niveauEtudeEnum = :niveauEtudeEnum ");
        }
        if (niveauPosteEnum != null ) {
        queryStringBuilder.append(" AND o.niveauPosteEnum = :niveauPosteEnum ");
        }
        if (typeContratEnum != null ) {
        queryStringBuilder.append(" AND o.typeContratEnum = :typeContratEnum ");
        }
        if (filterSalaireMin != null ) {
        queryStringBuilder.append(" AND o.salaire >= :filterSalaireMin ");
        }
        if (filterSalaireMax != null ) {
        queryStringBuilder.append(" AND o.salaire <= :filterSalaireMax ");
        }
        if (filterAnneeExpMin != null ) {
        queryStringBuilder.append(" AND o.anneeExperience >= :filterAnneeExpMin ");
        }
        if (filterAnneeExpMax != null ) {
        queryStringBuilder.append(" AND o.anneeExperience <= :filterAnneeExpMax ");
        }
        
        
        

        Query q = em.createQuery(queryStringBuilder.toString());
        if (secteurActivite != null ) {
        q.setParameter("secteurActivite", secteurActivite);
        }
        if (niveauEtudeEnum != null ) {
        q.setParameter("niveauEtudeEnum", niveauEtudeEnum);
        }
        if (niveauPosteEnum != null ) {
        q.setParameter("niveauPosteEnum", niveauPosteEnum);
        }
        if (typeContratEnum != null ) {
        q.setParameter("typeContratEnum", typeContratEnum);
        }
        if (filterSalaireMin != null ) {
        q.setParameter("filterSalaireMin", filterSalaireMin);
        }
        if (filterSalaireMax != null ) {
        q.setParameter("filterSalaireMax", filterSalaireMax);
        }
        if (filterAnneeExpMin != null ) {
        q.setParameter("filterAnneeExpMin", filterAnneeExpMin);
        }
        if (filterAnneeExpMax != null ) {
        q.setParameter("filterAnneeExpMax", filterAnneeExpMax);
        }
        
                
        
        try{
            filtredOffersList= new ArrayList<>(q.getResultList());
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return filtredOffersList;
    }

public ArrayList<Offre> searchByKeyWord(String keyWord){

    ArrayList<Offre> filtredOffersList=new ArrayList<>();
    
    Query q=em.createNamedQuery("Offre.searchByKeyWord").setParameter("keyWord",keyWord);
    try{
        filtredOffersList= new ArrayList<>(q.getResultList());
    }catch(Exception e){
        e.printStackTrace();
    }
    
    System.out.println("liste search by keyword "+filtredOffersList);
    return filtredOffersList;   
    
}    
    
    
}
