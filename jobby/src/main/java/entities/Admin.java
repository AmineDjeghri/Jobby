/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AmineD
 */
@Entity
@Table(name = "admin_model")
@XmlRootElement
public class Admin extends Membre implements Serializable {

    private static final long serialVersionUID = 1L;

    public Admin() {
    }

    public Admin(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "entities.Admin[ id=" + id + " ]";
    }
    
}
