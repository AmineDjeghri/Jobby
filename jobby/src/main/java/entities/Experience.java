/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumerations.NiveauPosteEnum;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AmineD
 */
@Entity
@Table(name = "experience")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Experience.findAll", query = "SELECT e FROM Experience e")
    , @NamedQuery(name = "Experience.findById", query = "SELECT e FROM Experience e WHERE e.id = :id")
    , @NamedQuery(name = "Experience.findByTitrePoste", query = "SELECT e FROM Experience e WHERE e.titrePoste = :titrePoste")
    , @NamedQuery(name = "Experience.findByNomEntreprise", query = "SELECT e FROM Experience e WHERE e.nomEntreprise = :nomEntreprise")
    , @NamedQuery(name = "Experience.findByDateDebut", query = "SELECT e FROM Experience e WHERE e.dateDebut = :dateDebut")
    , @NamedQuery(name = "Experience.findByDateFin", query = "SELECT e FROM Experience e WHERE e.dateFin = :dateFin")
    , @NamedQuery(name = "Experience.findByDescription", query = "SELECT e FROM Experience e WHERE e.description = :description")})
public class Experience implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "titrePoste")
    private String titrePoste;
    @Size(max = 2147483647)
    @Column(name = "nomEntreprise")
    private String nomEntreprise;
    @Column(name = "dateDebut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Column(name = "dateFin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @Column(name = "description")
    private String description;
    private SecteurActivite secteurActivite;
    
    @Enumerated(EnumType.STRING) 
    private NiveauPosteEnum niveauPosteEnum;
    
    @JoinColumn(name = "cv_id", referencedColumnName = "id")
    @ManyToOne
    private Cv cv;

    public Experience() {
    }

    public Experience(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitrePoste() {
        return titrePoste;
    }

    public void setTitrePoste(String titrePoste) {
        this.titrePoste = titrePoste;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    public NiveauPosteEnum getNiveauPosteEnum() {
        return niveauPosteEnum;
    }

    public void setNiveauPosteEnum(NiveauPosteEnum niveauPosteEnum) {
        this.niveauPosteEnum = niveauPosteEnum;
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
        if (!(object instanceof Experience)) {
            return false;
        }
        Experience other = (Experience) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public SecteurActivite getSecteurActivite() {
        return secteurActivite;
    }

    public void setSecteurActivite(SecteurActivite secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    
    @Override
    public String toString() {
        return "entities.Experience[ id=" + id + " ]";
    }
    
}
