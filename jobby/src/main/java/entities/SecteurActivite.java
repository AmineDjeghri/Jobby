/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author AmineD
 */
@Entity
@NamedQueries({
 @NamedQuery(name = "SecteurActivite.findSecteursActivite", query = "SELECT sa FROM SecteurActivite sa")
})
public class SecteurActivite implements Serializable{
    
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public SecteurActivite() {
        
    }

    
    
    
    public SecteurActivite(String nomSecteurActivite) {
        this.nomSecteurActivite = nomSecteurActivite;
    }
    
    
    
    private String nomSecteurActivite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomSecteurActivite() {
        return nomSecteurActivite;
    }

    public void setNomSecteurActivite(String nomSecteurActivite) {
        this.nomSecteurActivite = nomSecteurActivite;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final SecteurActivite other = (SecteurActivite) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

 
    
    
}
