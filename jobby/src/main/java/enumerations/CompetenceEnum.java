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
public enum CompetenceEnum {
    C("C"),
    Java("Java"),
    CPLUSPLUS("C++");
    
    private String nomCompetence;
   private CompetenceEnum(String nomCompetence)
   {
      this.nomCompetence = nomCompetence;
   }

   public String toString()
   {
      return this.nomCompetence; 
   }
}
