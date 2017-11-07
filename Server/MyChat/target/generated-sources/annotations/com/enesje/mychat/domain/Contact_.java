package com.enesje.mychat.domain;

import com.enesje.mychat.domain.Conversation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T23:17:30")
@StaticMetamodel(Contact.class)
public class Contact_ { 

    public static volatile SingularAttribute<Contact, Boolean> gotNewMessages;
    public static volatile SingularAttribute<Contact, String> id;
    public static volatile ListAttribute<Contact, Conversation> conversations;
    public static volatile SingularAttribute<Contact, String> username;

}