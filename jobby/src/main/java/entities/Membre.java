/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author AmineD
 */
@Entity
@Table(name = "membre_model")
@XmlRootElement
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(name = "Membre.findAll", query = "SELECT m FROM Membre m")
    , @NamedQuery(name = "Membre.findById", query = "SELECT m FROM Membre m WHERE m.id = :id")
    , @NamedQuery(name = "Membre.findByEmail", query = "SELECT m FROM Membre m WHERE m.email = :email")
    , @NamedQuery(name = "Membre.findByMdp", query = "SELECT m FROM Membre m WHERE m.mdp = :mdp")
    , @NamedQuery(name = "Membre.findByEmailAndMdp", query = "SELECT m FROM Membre m WHERE m.email = :email AND m.mdp = :mdp ")
    , @NamedQuery(name = "Membre.findByNom", query = "SELECT m FROM Membre m WHERE m.nom = :nom")
    , @NamedQuery(name = "Membre.findByPrenom", query = "SELECT m FROM Membre m WHERE m.prenom = :prenom")
    , @NamedQuery(name = "Membre.countByMargeDateCreation", query = "SELECT COUNT(m) FROM Membre m WHERE m.dateCreation >= :dateCreation1 AND  m.dateCreation <:dateCreation2 ")
    , @NamedQuery(name = "Membre.countCandidatsByMargeDateCreation", query = "SELECT COUNT(m) FROM Membre m WHERE m.dateCreation >= :dateCreation1 AND  m.dateCreation <:dateCreation2 AND m.dtype = 'Candidat'")
    , @NamedQuery(name = "Membre.countRecruteursByMargeDateCreation", query = "SELECT COUNT(m) FROM Membre m WHERE m.dateCreation >= :dateCreation1 AND  m.dateCreation <:dateCreation2 AND m.dtype = 'Recruteur'")
    , @NamedQuery(name = "Membre.findByCompteActivation", query = "SELECT m FROM Membre m WHERE m.compteActivation = :compteActivation")
    , @NamedQuery(name = "Membre.countByMargeDateDerniereConx", query = "SELECT COUNT(m) FROM Membre m WHERE m.dateDerniereConx >= :dateDerniereConx1 AND  m.dateDerniereConx <:dateDerniereConx2 ")    
    , @NamedQuery(name = "Membre.findByDateDerniereConx", query = "SELECT m FROM Membre m WHERE m.dateDerniereConx = :dateDerniereConx")
    , @NamedQuery(name = "Membre.findByDateDernierMsg", query = "SELECT m FROM Membre m WHERE m.dateDernierMsg = :dateDernierMsg")
    , @NamedQuery(name = "Membre.findByUrl", query = "SELECT m FROM Membre m WHERE m.url = :url")})
public class Membre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Integer id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(unique =true ,name = "email",nullable = false)
    protected String email;
    
    @Basic(optional = false)
    @NotNull
    @Size(max = 2147483647)
    @Column(name = "mdp",nullable = false)
    protected String mdp;


    @Size(max = 2147483647)
    @Column(name = "nom",nullable = false)
    protected String nom;
    
    @Basic(optional = false)
    @NotNull()    
    @Size(max = 2147483647)
    @Column(name = "prenom",nullable = false)
    protected String prenom;
    
    @Column(name = "dateCreation",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateCreation;
    
    @Column(name = "dateNaissance",nullable = false)
    @Temporal(TemporalType.DATE)
    protected Date dateNaissance;
    
    @Column(name = "compteActivation")
    protected Boolean compteActivation;
    
    @Column(name = "dateDerniereConx")
    @Temporal(TemporalType.TIMESTAMP) 
    protected Date dateDerniereConx;
    
    @Column(name = "dateDernierMsg")
    @Temporal(TemporalType.DATE)
    protected Date dateDernierMsg;
    
    @Size(max = 2147483647)
    @Column(name = "url")
    protected String url;
    
    @Column(name = "dtype",nullable = false)
    protected String dtype;
   

    public Membre() {
    }
    
    public boolean hasType(String typeMembre){
        return dtype.equals(typeMembre);
    }
    
    public Membre(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Boolean getCompteActivation() {
        return compteActivation;
    }

    public void setCompteActivation(Boolean compteActivation) {
        this.compteActivation = compteActivation;
    }

    public Date getDateDerniereConx() {
        return dateDerniereConx;
    }

    public void setDateDerniereConx(Date dateDerniereConx) {
        this.dateDerniereConx = dateDerniereConx;
    }

    public Date getDateDernierMsg() {
        return dateDernierMsg;
    }

    public void setDateDernierMsg(Date dateDernierMsg) {
        this.dateDernierMsg = dateDernierMsg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
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
        if (!(object instanceof Membre)) {
            return false;
        }
        Membre other = (Membre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Membre[ id=" + id + " ]";
    }

   


    
}
