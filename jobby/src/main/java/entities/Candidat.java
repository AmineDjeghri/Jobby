/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumerations.SituationFamilialeEnum;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AmineD
 */
@Entity
@Table(name = "candidat_model")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Candidat.countMembreWithActiveCv", query = "SELECT COUNT(c) FROM Candidat c WHERE c.cvActif IS NOT NULL")})
public class Candidat extends Membre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "tel_condidat")
    private String telCondidat;
    @Size(max = 2147483647)
    @Column(name = "adresseCandidat")
    private String adresseCandidat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="candidat")
    private ArrayList<Cv> listeCv;
    @JoinColumn(name = "cvActif_id")
    private Cv cvActif;
    private boolean serviceMilitaire;
    @Enumerated(EnumType.STRING) 
    private SituationFamilialeEnum situationFamilialeEnum;


    public Candidat() {
        listeCv=new ArrayList<>();
    }

    public Candidat(Integer id) {
        this.id = id;
    }

    public String getTelCondidat() {
        return telCondidat;
    }

    public void setTelCondidat(String telCondidat) {
        this.telCondidat = telCondidat;
    }

    public String getAdresseCandidat() {
        return adresseCandidat;
    }

    public void setAdresseCandidat(String adresseCandidat) {
        this.adresseCandidat = adresseCandidat;
    }


    public ArrayList<Cv> getListeCv() {
        return listeCv;
    }

    public void setListeCv(ArrayList<Cv> listeCv) {
        this.listeCv = listeCv;
    }

    public Cv getCvActif() {
        return cvActif;
    }

    public void setCvActif(Cv cvActif) {
        this.cvActif = cvActif;
    }

    public boolean isServiceMilitaire() {
        return serviceMilitaire;
    }

    public void setServiceMilitaire(boolean serviceMilitaire) {
        this.serviceMilitaire = serviceMilitaire;
    }

    public SituationFamilialeEnum getSituationFamilialeEnum() {
        return situationFamilialeEnum;
    }

    public void setSituationFamilialeEnum(SituationFamilialeEnum situationFamilialeEnum) {
        this.situationFamilialeEnum = situationFamilialeEnum;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

   
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Candidat)) {
            return false;
        }
        Candidat other = (Candidat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Candidat[ id=" + id + " ]";
    }
    
}
