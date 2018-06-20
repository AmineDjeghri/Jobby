/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author AmineD
 */
@Embeddable
public class CandidaturePK implements Serializable{
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cv_id")
    private int cvId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "offre_id")
    private int offreId;
    
     public CandidaturePK() {
    }

    public CandidaturePK(int cvId, int offreId) {
        this.cvId = cvId;
        this.offreId = offreId;
    }

    public int getCvId() {
        return cvId;
    }

    public void setCvId(int cvId) {
        this.cvId = cvId;
    }

    public int getOffreId() {
        return offreId;
    }

    public void setOffreId(int offreId) {
        this.offreId = offreId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cvId;
        hash += (int) offreId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CandidaturePK)) {
            return false;
        }
        CandidaturePK other = (CandidaturePK) object;
        if (this.cvId != other.cvId) {
            return false;
        }
        if (this.offreId != other.offreId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "a.Candidature2PK[ cvId=" + cvId + ", offreId=" + offreId + " ]";
    }
}
