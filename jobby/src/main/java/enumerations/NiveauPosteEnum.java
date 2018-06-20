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
public enum NiveauPosteEnum {
    Stagiaire("Stagiaire/Etudiant"),
    JeuneDiplome("Jeune Diplomé"),
    Debutant("Débutant"),
    Experimente("Experimenté"),
    ResponsableEquipe("Responsable d'équipe"),
    Manager("Manager"),
    Cadre("Cadre dirigeant");
    
    private String nomNiveauPoste;
    private NiveauPosteEnum(String nomNiveauPoste)
    {
        this.nomNiveauPoste = nomNiveauPoste;
    }

    public String getNomNiveauPoste() {
        return nomNiveauPoste;
    }
    
    

    public String toString()
    {
        return this.nomNiveauPoste;
    }
    
}
