/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enesje.mychat.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eirik
 */
@Entity 
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable {

    @Id
    private String id;
    
    @XmlTransient
    @ManyToOne
    private Conversation conversation;
    
    private String message;
    
    @ManyToOne
    private Contact sender;
    
    public Message(String id,Conversation conversation,Contact sender,String message)
    {
        this.id = id;
        this.message = message;
        this.conversation = conversation;
        this.sender = sender;
    }
    
    public Message(){}
    
    public String getMessage()
    {
        return this.message;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.enesje.chatapplication.domain.Message[ id=" + id + " ]";
    }
    
}
