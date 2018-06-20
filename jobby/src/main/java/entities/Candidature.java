/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumerations.TypeReponseEnum;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author AmineD
 */

@Entity(name = "candidature")
@NamedQueries({
      @NamedQuery(name = "Candidature.findByCvId", query = "SELECT c FROM candidature c WHERE c.id.cvId = :cvId")
    , @NamedQuery(name = "Candidature.findByOffreId", query = "SELECT c FROM candidature c WHERE c.id.offreId = :offreId")
    , @NamedQuery(name = "Candidature.countByOffreId", query = "SELECT COUNT(c) FROM candidature c WHERE c.id.offreId = :offreId")})
public class Candidature implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private CandidaturePK id;    
 
    @ManyToOne
    @JoinColumn(name = "cv_id",insertable = false ,updatable = false)
    private Cv cv;  
   
    @ManyToOne
    @JoinColumn(name = "offre_id",insertable = false ,updatable = false)
    private Offre offre;
    
    @Column(name = "dateCandidature")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateCandidature;
    
    @Enumerated(EnumType.STRING)
    private TypeReponseEnum typeReponse;
    
    private boolean  reponse;

    public Candidature() {
        typeReponse=TypeReponseEnum.waiting;
    }
    
    public CandidaturePK getId() {
        return id;
    }

    public void setId(CandidaturePK id) {
        this.id = id;
    }

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Date getDateCandidature() {
        return dateCandidature;
    }

    public void setDateCandidature(Date dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public boolean isReponse() {
        return reponse;
    }

    public void setReponse(boolean reponse) {
        this.reponse = reponse;
    }

    public TypeReponseEnum getTypeReponse() {
        return typeReponse;
    }

    public void setTypeReponse(TypeReponseEnum typeReponse) {
        this.typeReponse = typeReponse;
    }
    
    

    @Override
    public String toString() {
        return "Candidature{" + "id=" + id + '}';
    }
    
    
    
    
}
