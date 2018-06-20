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
public enum TypeReponseEnum {
    Accepted("Accepté"),waiting("En attente"),refused("refusé");
    
    String nomTypeReponse;

    private TypeReponseEnum(String nomTypeReponse) {
        this.nomTypeReponse = nomTypeReponse;
    }

    public String getNomTypeReponse() {
        return nomTypeReponse;
    }
   
}
