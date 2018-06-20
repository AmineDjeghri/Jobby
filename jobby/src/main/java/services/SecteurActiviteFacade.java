/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.SecteurActivite;
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
public class SecteurActiviteFacade extends AbstractFacade<SecteurActivite> {

    @PersistenceContext(unitName = "com.mycompany_jobby_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecteurActiviteFacade() {
        super(SecteurActivite.class);
    }
    
    public ArrayList<SecteurActivite> findSecteursActivite(){
        ArrayList<SecteurActivite>listeCandidatures=null;
        Query q=em.createNamedQuery("SecteurActivite.findSecteursActivite");
        
        try{
            listeCandidatures=new ArrayList<>(q.getResultList());
        }catch(Exception e){
            System.out.println(e);
        }
        
        return listeCandidatures;
    }   
}
