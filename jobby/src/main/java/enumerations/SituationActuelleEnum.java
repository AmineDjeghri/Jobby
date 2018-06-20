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
public enum SituationActuelleEnum {
    EnRecherche("En recherche de travail"),
    Ouvert("Ouvert au propositions"),
    PasEnRecherche("Pas en recherche de travail");
    
    private String nomSituation;
    
 private SituationActuelleEnum(String nomSituation){
    this.nomSituation=nomSituation;
}

    public String getNomSituation() {
        return nomSituation;
    }

    @Override
    public String toString() {
        return "SituationActuelleEnum{" + "nomSituation=" + nomSituation + '}';
    }
 
 
}

