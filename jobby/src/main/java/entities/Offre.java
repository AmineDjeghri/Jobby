/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumerations.NiveauEtudeEnum;
import enumerations.NiveauPosteEnum;
import enumerations.TypeContratEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author AmineD
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Offre.findActiveOffers", query = "SELECT o FROM Offre o WHERE o.active = true")
  , @NamedQuery(name = "Offre.countActiveOffers", query = "SELECT COUNT(o) FROM Offre o WHERE o.active = true")  
  , @NamedQuery(name = "Offre.countOffreBySecteur", query = "SELECT COUNT(o) FROM Offre o WHERE o.secteurActivite = :secteurActivite")
  , @NamedQuery(name = "Offre.countOffersByMargeDateCreation", query = "SELECT COUNT(o) FROM Offre o WHERE o.dateCreation >= :dateCreation1 AND o.dateCreation < :dateCreation2")
  , @NamedQuery(name = "Offre.searchByKeyWord", query = "SELECT o FROM Offre o,Recruteur r WHERE  o.active = true AND o.recruteur.id=r.id AND (o.titre LIKE CONCAT('%', :keyWord, '%') OR o.description LIKE CONCAT('%', :keyWord, '%') OR o.adresse LIKE CONCAT('%', :keyWord, '%') OR r.nomEntreprise LIKE CONCAT('%', :keyWord, '%'))")})

public class Offre implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModification;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSupression;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateActivation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDesactivation;
    
    
    private Double latitude;
    private Double longitude;
    private String titre;
    private Boolean active;
    private String adresse;
    private String description;
    @OneToOne
    private SecteurActivite secteurActivite;
    private NiveauEtudeEnum niveauEtudeEnum;
    private NiveauPosteEnum niveauPosteEnum;
    private int anneeExperience;
    private int salaire;
    private TypeContratEnum typeContratEnum;
    
    @ManyToOne
    private Recruteur recruteur;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "offre")
    private ArrayList<Candidature> listeCandidatures;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="offre")
    private ArrayList<Langue_offre> listeLangues_offre;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="offre")
    private ArrayList<Competence_offre> listeCompetences_offre;
    


    public Offre() {
        listeLangues_offre=new ArrayList<>();
        listeCompetences_offre=new ArrayList<>();
    }
        

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recruteur getRecruteur() {
        return recruteur;
    }

    public void setRecruteur(Recruteur recruteur) {
        this.recruteur = recruteur;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    } 

    public ArrayList<Candidature> getListeCandidatures() {
        return listeCandidatures;
    }

    public void setListeCandidatures(ArrayList<Candidature> listeCandidatures) {
        this.listeCandidatures = listeCandidatures;
    }

    public ArrayList<Langue_offre> getListeLangues_offre() {
        return listeLangues_offre;
    }

    public void setListeLangues_offre(ArrayList<Langue_offre> listeLangues_offre) {
        this.listeLangues_offre = listeLangues_offre;
    }

    public ArrayList<Competence_offre> getListeCompetences_offre() {
        return listeCompetences_offre;
    }

    public void setListeCompetences_offre(ArrayList<Competence_offre> listeCompetences_offre) {
        this.listeCompetences_offre = listeCompetences_offre;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SecteurActivite getSecteurActivite() {
        return secteurActivite;
    }

    public void setSecteurActivite(SecteurActivite secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    public NiveauEtudeEnum getNiveauEtudeEnum() {
        return niveauEtudeEnum;
    }

    public void setNiveauEtudeEnum(NiveauEtudeEnum niveauEtudeEnum) {
        this.niveauEtudeEnum = niveauEtudeEnum;
    }

    public int getAnneeExperience() {
        return anneeExperience;
    }

    public void setAnneeExperience(int anneeExperience) {
        this.anneeExperience = anneeExperience;
    }

    public int getSalaire() {
        return salaire;
    }

    public void setSalaire(int salaire) {
        this.salaire = salaire;
    }

    public TypeContratEnum getTypeContratEnum() {
        return typeContratEnum;
    }

    public void setTypeContratEnum(TypeContratEnum typeContratEnum) {
        this.typeContratEnum = typeContratEnum;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public NiveauPosteEnum getNiveauPosteEnum() {
        return niveauPosteEnum;
    }

    public void setNiveauPosteEnum(NiveauPosteEnum niveauPosteEnum) {
        this.niveauPosteEnum = niveauPosteEnum;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public Date getDateSupression() {
        return dateSupression;
    }

    public void setDateSupression(Date dateSupression) {
        this.dateSupression = dateSupression;
    }

    public Date getDateActivation() {
        return dateActivation;
    }

    public void setDateActivation(Date dateActivation) {
        this.dateActivation = dateActivation;
    }


    public Date getDateDesactivation() {
        return dateDesactivation;
    }

    public void setDateDesactivation(Date dateDesactivation) {
        this.dateDesactivation = dateDesactivation;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Offre other = (Offre) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Offre{" + "id=" + id + '}';
    }

    
    
    
    
    
}
