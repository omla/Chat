/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enesje.mychat.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eirik
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CoversationModel implements Serializable {
    private String sender;
    private String lastMessage;
    private String conversationID;
    private String senderID;

    public CoversationModel(String conversationID, String sender, String lastMessage, String senderID) {
        this.conversationID = conversationID;
        this.lastMessage = lastMessage;
        this.sender = sender;
        this.senderID = senderID;
    }
    
    public CoversationModel() {
    
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getSender() {
        return sender;
    }

    public String getConversationID() {
        return conversationID;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
