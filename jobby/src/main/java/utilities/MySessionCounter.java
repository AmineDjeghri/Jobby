/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import entities.Membre;
import java.util.Date;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import services.MembreFacade;

/**
 *
 * @author AmineD
 */
public class MySessionCounter implements HttpSessionListener{
     
      public static int connectedUsers = 0;
 
    @EJB
    MembreFacade membreFacade;
                          
    @Override
    public void sessionCreated(HttpSessionEvent se) {
      
            
        System.out.println("session created "+connectedUsers );
    }

    @Override
    @PreDestroy
    public void sessionDestroyed(HttpSessionEvent se) {
        try{
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Membre membre=(Membre)session.getAttribute("membre");
            System.out.println("membre "+membre);
            membre.setDateDerniereConx(new Date());
            membreFacade.edit(membre);
            System.out.println("membre date derniere conx "+membre.getDateDerniereConx());
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        connectedUsers--;
       System.out.println("session destroyed "+connectedUsers );
      
        

    }
  
    
}
