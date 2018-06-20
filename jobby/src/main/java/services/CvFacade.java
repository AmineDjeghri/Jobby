/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Cv;
import entities.SecteurActivite;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AmineD
 */
@Stateless
public class CvFacade extends AbstractFacade<Cv> {

    @PersistenceContext(unitName = "com.mycompany_jobby_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CvFacade() {
        super(Cv.class);
    }
    
    public long countCandidatEnRechercheBySecteur(SecteurActivite secteurActivite){
        
    long nbr=0;    
        Query q=em.createNamedQuery("Cv.countCandidatEnRechercheBySecteur").setParameter("secteurActivite", secteurActivite);
        
        try{
            
            nbr=(long)q.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return nbr;
    }
    
}
