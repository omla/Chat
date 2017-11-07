/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enesje.mychat.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.PERSIST;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
public class Contact implements Serializable {

    @Id String id;
    
    private String username;
    
    @XmlTransient
    private boolean gotNewMessages = false;
            
    @ManyToMany(cascade=PERSIST)
    @XmlTransient
    private List<Conversation> conversations;
    
    public Contact(String contactID,String username)
    {
        this.id = contactID;
        this.username = username;
        conversations = new ArrayList<>();
        
    }
    
    public Contact(){}

    public String getUsername() {
        return username;
    }

    public boolean isGotNewMessages() {
        return gotNewMessages;
    }

    public void setGotNewMessages(boolean gotNewMessages) {
        this.gotNewMessages = gotNewMessages;
    }

    
    
    
    public void setConversations(ArrayList<Conversation> conversations)
    {
        this.conversations = conversations;
    }
    
    
    public void addConversation(Conversation conversation)
    {
        conversations.add(conversation);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String contactID) {
        this.id = contactID;
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
        if (!(object instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.enesje.chatapplication.domain.Contact[ id=" + id + " ]";
    }
    
}
