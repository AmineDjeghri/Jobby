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
public enum NiveauEtudeEnum {
    PasDimplome("Pas de diplome"),
    Secondaire("Secondaire"),
    Terminal("Terminal"),
    Baccalaureat("Baccalauréat"),
    Formation("Formation Professionel"),
    UnivSansDiplome("Universitaire Sans Diplome"),
    Licence2("TS / Licence,Bac +2"),
    Licence3("Licence,Bac +3"),
    Master1("Master 1,Bac +4"),
    Master2("Master 2,Bac +5"),
    Doctorat("Doctorat");
    
    private String nomNiveauEtude;
    private NiveauEtudeEnum(String nomNiveauEtude)
    {
        this.nomNiveauEtude = nomNiveauEtude;
    }

    public String getNomNiveauEtude() {
        return nomNiveauEtude;
    }

    
    
    public String toString()
    {
        return this.nomNiveauEtude;
    }
    
}
