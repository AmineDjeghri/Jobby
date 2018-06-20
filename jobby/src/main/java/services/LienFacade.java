/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Lien;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AmineD
 */
@Stateless
public class LienFacade extends AbstractFacade<Lien> {

    @PersistenceContext(unitName = "com.mycompany_jobby_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LienFacade() {
        super(Lien.class);
    }
    
}
