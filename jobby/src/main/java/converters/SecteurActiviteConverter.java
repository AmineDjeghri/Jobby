/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.SecteurActivite;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author AmineD
 */
@FacesConverter(forClass = SecteurActivite.class)
public class SecteurActiviteConverter implements Converter{
        @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("debut converter s to o");
       if (value != null && !"".equals(value.trim()) && !"null".equals(value)) {
            SecteurActivite secteurActivite = new SecteurActivite();
            secteurActivite.setId(Integer.parseInt(value));
            System.out.println("secteur activie converter id" +secteurActivite.getId());
            return secteurActivite;
        }
        
        
        System.out.println("probleme! String null ou bien vide");
        return null; 
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
               if (value instanceof SecteurActivite) {
            SecteurActivite secteurActivite = new SecteurActivite();
            secteurActivite = (SecteurActivite) value;
            System.out.println(" de Objet ModelComposant à String ");
            return secteurActivite.getId().toString();
        }
          return "0";
    }  
    
}
