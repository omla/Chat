package com.enesje.mychat.domain;

import com.enesje.mychat.domain.Contact;
import com.enesje.mychat.domain.Message;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T23:17:30")
@StaticMetamodel(Conversation.class)
public class Conversation_ { 

    public static volatile ListAttribute<Conversation, Contact> parts;
    public static volatile ListAttribute<Conversation, Message> messages;
    public static volatile SingularAttribute<Conversation, Long> id;

}