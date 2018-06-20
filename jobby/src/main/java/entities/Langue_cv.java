/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumerations.LangueEnum;
import java.io.Serializable;
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

/**
 *
 * @author AmineD
 */
@Entity
public class Langue_cv implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Enumerated(EnumType.STRING)
    private LangueEnum langueEnum;
    
    private int niveauLangue;
    
    @JoinColumn(name = "cv_id", referencedColumnName = "id")
    @ManyToOne
    private Cv cv;
    
    

    public LangueEnum getLangueEnum() {
        return langueEnum;
    }

    public void setLangueEnum(LangueEnum langueEnum) {
        this.langueEnum = langueEnum;
    }

    public int getNiveauLangue() {
        return niveauLangue;
    }

    public void setNiveauLangue(int niveauLangue) {
        this.niveauLangue = niveauLangue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }
    
    
    
}
