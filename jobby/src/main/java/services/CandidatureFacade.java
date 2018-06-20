/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Candidature;
import entities.Cv;
import entities.Offre;
import entities.SecteurActivite;
import enumerations.NiveauEtudeEnum;
import enumerations.SituationFamilialeEnum;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AmineD
 */
@Stateless
public class CandidatureFacade extends AbstractFacade<Candidature> {

    @PersistenceContext(unitName = "com.mycompany_jobby_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CandidatureFacade() {
        super(Candidature.class);
    }
    
    public ArrayList<Candidature> findCandidaturesByCv(int cvId){
        ArrayList<Candidature>listeCandidatures=null;
        Query q=em.createNamedQuery("Candidature.findByCvId");
        q.setParameter("cvId", cvId);
        try{
            listeCandidatures=new ArrayList<>(q.getResultList());
        }catch(Exception e){
            System.out.println(e);
        }
        
        return listeCandidatures;
    }
    
    
    public ArrayList<Candidature> findCandidaturesByOffre(int offreId){
        ArrayList<Candidature>listeCandidatures=null;
        Query q=em.createNamedQuery("Candidature.findByOffreId");
        q.setParameter("offreId", offreId);
        try{
            listeCandidatures=new ArrayList<>(q.getResultList());
        }catch(Exception e){
            System.out.println(e);
        }
        
        return listeCandidatures;
    }
    
    public ArrayList<Candidature> filterCandidaturesByOffreAndParams(Integer offreId,SecteurActivite secteurActivite, NiveauEtudeEnum niveauEtudeEnum,SituationFamilialeEnum situationFamilialeEnum,Boolean serviceMilitaire,Integer filterAnneeExpMin,Integer filterAnneeExpMax) {

            ArrayList<Candidature> filtredOffersList=new ArrayList<>();
            System.out.println("0 step");
            StringBuilder queryStringBuilder = new StringBuilder();
            queryStringBuilder.append("SELECT c FROM candidature c,Cv cv,Candidat cn WHERE c.id.offreId = :offreId AND c.cv.id=cv.id AND cv.candidat.id =cn.id");


            if (secteurActivite != null ) {
            queryStringBuilder.append(" AND cv.secteurActivite = :secteurActivite ");
            }
            if (niveauEtudeEnum != null ) {
            queryStringBuilder.append(" AND cv.niveauEtudeEnum = :niveauEtudeEnum ");
            }
            if (situationFamilialeEnum != null ) {
            queryStringBuilder.append(" AND cn.situationFamilialeEnum = :situationFamilialeEnum ");
            }
            if (serviceMilitaire != null ) {
            queryStringBuilder.append(" AND cn.serviceMilitaire = :serviceMilitaire ");
            }
            if (filterAnneeExpMin != null ) {
            queryStringBuilder.append(" AND cv.anneeExperience >= :filterAnneeExpMin ");
            }
            if (filterAnneeExpMax != null ) {
            queryStringBuilder.append(" AND cv.anneeExperience <= :filterAnneeExpMax ");
            }




            Query q = em.createQuery(queryStringBuilder.toString());
            q.setParameter("offreId", offreId);
            
            if (secteurActivite != null ) {
            q.setParameter("secteurActivite", secteurActivite);
            }
            if (niveauEtudeEnum != null ) {
            q.setParameter("niveauEtudeEnum", niveauEtudeEnum);
            }
            if (situationFamilialeEnum != null ) {
            q.setParameter("situationFamilialeEnum", situationFamilialeEnum);
            }
            if (serviceMilitaire != null ) {
            q.setParameter("serviceMilitaire", serviceMilitaire);
            }
            if (filterAnneeExpMin != null ) {
            q.setParameter("filterAnneeExpMin", filterAnneeExpMin);
            }
            if (filterAnneeExpMax != null ) {
            q.setParameter("filterAnneeExpMax", filterAnneeExpMax);
            }


            System.out.println("4th step");
            try{
                filtredOffersList= new ArrayList<>(q.getResultList());
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("liste query "+filtredOffersList);
            return filtredOffersList;
    }    

    
    
    
}
