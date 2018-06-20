/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumerations.NiveauEtudeEnum;
import enumerations.NiveauPosteEnum;
import enumerations.SituationActuelleEnum;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AmineD
 */
@Entity
@Table(name = "cv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cv.findAll", query = "SELECT c FROM Cv c")
    , @NamedQuery(name = "Cv.findById", query = "SELECT c FROM Cv c WHERE c.id = :id")
    , @NamedQuery(name = "Cv.findByCandidat", query = "SELECT c FROM Cv c WHERE c.candidat = :candidat")
    , @NamedQuery(name = "Cv.countCandidatEnRechercheBySecteur", query = "SELECT COUNT(c) FROM Cv c WHERE c.secteurActivite = :secteurActivite AND c.situationActuelle = enumerations.SituationActuelleEnum.EnRecherche")
//    , @NamedQuery(name = "Cv.findByQualitesHumaines", query = "SELECT c FROM Cv c WHERE c.qualitesHumaines = :qualitesHumaines")
    , @NamedQuery(name = "Cv.findByInformationsComplementaires", query = "SELECT c FROM Cv c WHERE c.informationsComplementaires = :informationsComplementaires")})
public class Cv implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column
    private String titreCv;
    @Size(max = 50)
    @Column(name = "qualites_humaines")
    private String qualitesHumaines;
    @Size(max = 2147483647)
    @Column(name = "informations_complementaires")
    private String informationsComplementaires;
    private Integer anneeExperience;
    private SecteurActivite secteurActivite;
    
    //Enum
    @Enumerated(EnumType.STRING)
    private SituationActuelleEnum situationActuelle;

    @Enumerated(EnumType.STRING) 
    private NiveauPosteEnum niveauPosteEnum;
    @Enumerated(EnumType.STRING) 
    private NiveauEtudeEnum niveauEtudeEnum;
    
    
    
    
    @JoinColumn(name = "candidat_id", referencedColumnName = "id", updatable = false)
    @ManyToOne
    private Candidat candidat;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="cv")
    private ArrayList<Lien> listeLiens;
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="cv")
    private ArrayList<Publication> listePublications;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "cv")
    private ArrayList<Candidature> listeCandidatures;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="cv")
    private ArrayList<Langue_cv> listeLangues_cv;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="cv")
    private ArrayList<Competence_cv> listeCompetences_cv;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="cv")
    private ArrayList<Formation> listeFormations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="cv")
    private ArrayList<Experience> listeExperiences;
    

    public Cv() {
       listeLangues_cv=new ArrayList<>();
       listeCompetences_cv=new ArrayList<>();
       listeExperiences=new ArrayList<>();
       listeFormations=new ArrayList<>();
       listeLiens =new ArrayList<>();
       listePublications=new ArrayList<>();
    }

    public Cv(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getInformationsComplementaires() {
        return informationsComplementaires;
    }

    public void setInformationsComplementaires(String informationsComplementaires) {
        this.informationsComplementaires = informationsComplementaires;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public SituationActuelleEnum getSituationActuelle() {
        return situationActuelle;
    }

    public void setSituationActuelle(SituationActuelleEnum situationActuelle) {
        this.situationActuelle = situationActuelle;
    }

    public String getQualitesHumaines() {
        return qualitesHumaines;
    }

    public void setQualitesHumaines(String qualitesHumaines) {
        this.qualitesHumaines = qualitesHumaines;
    }

    public SecteurActivite getSecteurActivite() {
        return secteurActivite;
    }

    public void setSecteurActivite(SecteurActivite secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    public String getTitreCv() {
        return titreCv;
    }

    public void setTitreCv(String titreCv) {
        this.titreCv = titreCv;
    }

    public NiveauPosteEnum getNiveauPosteEnum() {
        return niveauPosteEnum;
    }

    public void setNiveauPosteEnum(NiveauPosteEnum niveauPosteEnum) {
        this.niveauPosteEnum = niveauPosteEnum;
    }    

    public NiveauEtudeEnum getNiveauEtudeEnum() {
        return niveauEtudeEnum;
    }

    public void setNiveauEtudeEnum(NiveauEtudeEnum niveauEtudeEnum) {
        this.niveauEtudeEnum = niveauEtudeEnum;
    }

    public Integer getAnneeExperience() {
        return anneeExperience;
    }

    public void setAnneeExperience(Integer anneeExperience) {
        this.anneeExperience = anneeExperience;
    }

    public ArrayList<Lien> getListeLiens() {
        return listeLiens;
    }

    public void setListeLiens(ArrayList<Lien> listeLiens) {
        this.listeLiens = listeLiens;
    }

    public ArrayList<Publication> getListePublications() {
        return listePublications;
    }

    public void setListePublications(ArrayList<Publication> listePublications) {
        this.listePublications = listePublications;
    }

    public ArrayList<Candidature> getListeCandidatures() {
        return listeCandidatures;
    }

    public void setListeCandidatures(ArrayList<Candidature> listeCandidatures) {
        this.listeCandidatures = listeCandidatures;
    }

    public ArrayList<Langue_cv> getListeLangues_cv() {
        return listeLangues_cv;
    }

    public void setListeLangues_cv(ArrayList<Langue_cv> listeLangues_cv) {
        this.listeLangues_cv = listeLangues_cv;
    }

    public ArrayList<Competence_cv> getListeCompetences_cv() {
        return listeCompetences_cv;
    }

    public void setListeCompetences_cv(ArrayList<Competence_cv> listeCompetences_cv) {
        this.listeCompetences_cv = listeCompetences_cv;
    }

    public ArrayList<Formation> getListeFormations() {
        return listeFormations;
    }

    public void setListeFormations(ArrayList<Formation> listeFormations) {
        this.listeFormations = listeFormations;
    }

    public ArrayList<Experience> getListeExperiences() {
        return listeExperiences;
    }

    public void setListeExperiences(ArrayList<Experience> listeExperiences) {
        this.listeExperiences = listeExperiences;
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
        if (!(object instanceof Cv)) {
            return false;
        }
        Cv other = (Cv) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "entities.Cv[ id=" + id + " ]";
    }



    
}
