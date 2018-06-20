/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumerations;

/**
 *
 * @author AmineD
 */
public enum TypeContratEnum {
    CDI("A durée indéterminée"),CDD("A durée déterminée"),CTT("De travail temporaire ou Intérim"),Apprentissage("D’apprentissage"),professionnalisation("De professionnalisation"),CUI("Unique d’insertion"),CAE("D’accompagnement dans l’emploi"),CIE("Initiative emploi");
    
    String nomTypeContrat;

    private TypeContratEnum(String nomTypeContrat) {
        this.nomTypeContrat = nomTypeContrat;
    }

    public String getNomTypeContrat() {
        return nomTypeContrat;
    }
  
}
