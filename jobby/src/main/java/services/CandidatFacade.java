/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Candidat;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AmineD
 */
@Stateless
public class CandidatFacade extends AbstractFacade<Candidat> {

    @PersistenceContext(unitName = "com.mycompany_jobby_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CandidatFacade() {
        super(Candidat.class);
    }
    
    public Integer countActiveCv(){
        Integer count=0;
        Query q=em.createNamedQuery("Candidat.countMembreWithActiveCv");
        try{
            count=Math.toIntExact((Long)q.getSingleResult());
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return count;
    }
}
