/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.Membre;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Membre.class)
public class CandidatConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        
        if (value != null && !"".equals(value.trim()) && !"null".equals(value)) {
            Membre m = new Membre();
            m.setId(Integer.parseInt(value));
            System.out.println("de string à Membre "+m.getId());
            return m;
        }
               
        System.out.println("probleme! String null ou bien vide");
        return null;
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println(" asString value = " + value);
        
        if (value instanceof Membre) {
            Membre f = new Membre();
            f = (Membre) value;
            System.out.println(" de Objet Membre à String ");
            return f.getId().toString();
        }
        
        return "0";
    }


    
}