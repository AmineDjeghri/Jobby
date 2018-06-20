/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AmineD
 */
@Entity
@Table(name = "recruteur_model")
@XmlRootElement
public class Recruteur extends Membre implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull   
    @Size(max = 2147483647)
    @Column(name = "nomEntreprise")
    private String nomEntreprise;
    
    @Basic(optional = false)
    @NotNull     
    @Size(max = 2147483647)
    @Column(name = "adresseEntreprise")
    private String adresseEntreprise;
    
    @Basic(optional = false)
    @NotNull    
    @Size(max = 2147483647)
    @Column(name = "telEntreprise")
    private String telEntreprise;
    
    @Column(name = "abonnement")
    private Boolean abonnement;
    
    @Size(max = 2147483647)
    @Column(name = "descEntreprise")
    private String descEntreprise;
    
    @Column(name = "compteVerification")
    private Boolean compteVerification;
    
    @OneToMany(mappedBy = "recruteur",cascade = CascadeType.ALL)
    private ArrayList<Offre> listeOffres;

    public Recruteur() {
        
    }


    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getAdresseEntreprise() {
        return adresseEntreprise;
    }

    public void setAdresseEntreprise(String adresseEntreprise) {
        this.adresseEntreprise = adresseEntreprise;
    }

    public String getTelEntreprise() {
        return telEntreprise;
    }

    public void setTelEntreprise(String telEntreprise) {
        this.telEntreprise = telEntreprise;
    }

    public Boolean getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Boolean abonnement) {
        this.abonnement = abonnement;
    }

    public String getDescEntreprise() {
        return descEntreprise;
    }

    public void setDescEntreprise(String descEntreprise) {
        this.descEntreprise = descEntreprise;
    }

    public Boolean getCompteVerification() {
        return compteVerification;
    }

    public void setCompteVerification(Boolean compteVerification) {
        this.compteVerification = compteVerification;
    }

    public ArrayList<Offre> getListeOffres() {
        return listeOffres;
    }

    public void setListeOffres(ArrayList<Offre> listeOffres) {
        this.listeOffres = listeOffres;
    }

    

    @Override
    public String toString() {
        return "entities.Recruteur[ id=" + id + " ]";
    }
    
}
