/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Membre;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import utilities.EncryptPassword;

/**
 *
 * @author AmineD
 */
@Stateless
public class MembreFacade extends AbstractFacade<Membre> {

    @PersistenceContext(unitName = "com.mycompany_jobby_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MembreFacade() {
        super(Membre.class);
    }

    
    public boolean findByEmail(String email){
        Query q=em.createNamedQuery("Membre.findByEmail").setParameter("email", email);
        try{
        if(q.getResultList().isEmpty())
            return false;
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return true;
    }
    
    public Membre loginByUID(String email,String mdp) throws NoSuchAlgorithmException{
        mdp=EncryptPassword.encyptMD5(mdp);
        Membre m;
        //declaration de la requete 
        Query q= em.createNamedQuery("Membre.findByEmailAndMdp").setParameter("email", email).setParameter("mdp",mdp);
        
    //    excution de la requette 
        try{
        m = (Membre) q.getSingleResult();
        System.out.println("membre"+m);
        }catch(Exception e)
        { System.out.println("Exception"+e);
         m=null;
        }
        return m;
   
    }

    public  long countTodayRegistredMembers(){
        long nbr=0;
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        
        
        Date date1=cal.getTime();
        
        Date date2=new Date();
            System.out.println("date 1 "+date1+" date2 "+date2);

        
        Query q=em.createNamedQuery("Membre.countByMargeDateCreation").setParameter("dateCreation1", date1).setParameter("dateCreation2", date2);
        
        try{
            
            nbr=(long)q.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
        }       
        return nbr;        
    }
    
    
    public  long countTodayLoggedInMembers(){
        long nbr=0;
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        
        
        Date date1=cal.getTime();
        
        Date date2=new Date();
            System.out.println("date 1 derniere conx "+date1+" date2 derniers conx"+date2);

        
        Query q=em.createNamedQuery("Membre.countByMargeDateDerniereConx").setParameter("dateDerniereConx1", date1).setParameter("dateDerniereConx2", date2);
        
        try{
            
            nbr=(long)q.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
        }       
        return nbr;        
    }
    
    public long countCandidatsByMargeDateCreation(Date startDate,Date endDate){
        long nbr=0;
        
          
        Query q=em.createNamedQuery("Membre.countCandidatsByMargeDateCreation").setParameter("dateCreation1", startDate).setParameter("dateCreation2", endDate);
        
        try{
            
            nbr=(long)q.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return nbr;
    } 
    public long countRecruteursByMargeDateCreation(Date startDate,Date endDate){
        long nbr=0;
        
          
        Query q=em.createNamedQuery("Membre.countRecruteursByMargeDateCreation").setParameter("dateCreation1", startDate).setParameter("dateCreation2", endDate);
        
        try{
            
            nbr=(long)q.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return nbr;
    } 
    
}
