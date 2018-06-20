/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.Competence;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author AmineD
 */
@FacesConverter(forClass = Competence.class)
public class CompetenceConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("debut converter s to o");
       if (value != null && !"".equals(value.trim()) && !"null".equals(value)) {
            Competence competence = new Competence();
            competence.setId(Integer.parseInt(value));
            System.out.println("competence converter id" +competence.getId());
            return competence;
        }
        
        
        System.out.println("probleme! String null ou bien vide");
        return null; 
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
               if (value instanceof Competence) {
            Competence competence = new Competence();
            competence = (Competence) value;
            System.out.println(" de Objet Competence à String ");
            return competence.getId().toString();
        }
          return "0";
    }  
    
}
