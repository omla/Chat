package com.enesje.mychat.domain;

import com.enesje.mychat.domain.Contact;
import com.enesje.mychat.domain.Conversation;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T23:17:30")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, Contact> sender;
    public static volatile SingularAttribute<Message, String> id;
    public static volatile SingularAttribute<Message, String> message;
    public static volatile SingularAttribute<Message, Conversation> conversation;

}