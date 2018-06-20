/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;


import entities.Cv;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author AmineD
 */
@FacesConverter(forClass = Cv.class)
public class CvConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("debut converter s to o");
       if (value != null && !"".equals(value.trim()) && !"null".equals(value)) {
            Cv cv = new Cv();
            cv.setId(Integer.parseInt(value));
            System.out.println("cv converter id" +cv.getId());
            return cv;
        }
        
        
        System.out.println("probleme! String null ou bien vide");
        return null; 
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
               if (value instanceof Cv) {
            Cv cv = new Cv();
            cv = (Cv) value;
            System.out.println(" de Objet ModelComposant à String ");
            return cv.getId().toString();
        }
          return "0";
    }  
}