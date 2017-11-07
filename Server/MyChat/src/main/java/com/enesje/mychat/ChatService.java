/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enesje.mychat;

import com.enesje.mychat.domain.Contact;
import com.enesje.mychat.domain.Conversation;
import com.enesje.mychat.domain.CoversationModel;
import com.enesje.mychat.domain.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author Nesjen
 */
@Path("chat")
@Stateless
public class ChatService {
    @PersistenceContext
    EntityManager em;
    
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("chatOne")
    public Contact getUser()
    {
        return new Contact();
    }
   
    //localhost/services/chat/user?name=test
    // Jax-RS

    /**
     *
     * @param name
     * @param message
     * @return
     */
    @GET
    @Path("user")
    public Contact findUser(@QueryParam("name") String name,@QueryParam("message") String message){
        return new Contact();
    }
    
    
    //http://localhost:8080/MyChat/services/chat/createuser?userid=Gard&username=test
    @GET
    @Path("createuser")
    @Produces(value = MediaType.TEXT_HTML)
    public Response createUser(@QueryParam("userid") String userID, @QueryParam("username") String username)
    {
        if(!checkIfUserExsist(userID))
        {
            Contact newContact = new Contact(userID,username);
            em.persist(newContact);
            return Response.ok().build();
        }else{
            return Response.serverError().build();
        }  
    }
    
    /*
    @GET
    @Path("getConversations")
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<Conversation> getContactConversations(@QueryParam("userid") String userID)
    {
        List<Conversation> conversations = em.createQuery("SELECT c from Conversation c where c.id = ''").getResultList();
      
    }
    */        
    
    
    //http://localhost:8080/MyChat/services/chat/getAllContacts
    @GET
    @Path("getAllContacts")
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<Contact> getAllContacts()
    {
        List<Contact> list = em.createQuery("SELECT c from Contact c").getResultList();
        System.out.println("Str::" + list.size());
        for(Contact t : list) { 
            System.out.println(t.getUsername());
        }
        
        return list;
    }
            
    
    
    public boolean checkIfUserExsist(String userID)
    {
        //INGEN SIKKERHET WHAT SO EVER. Banka inn rett i db.
        String query = "SELECT c from Contact c where c.id = '" + userID + "'";
        List<Contact> list = em.createQuery(query).getResultList();
        if(list.size() > 0)
        {
            return true;
        }else{
            return false;
        }
    }
    
    
    @GET
    @Path("getallconversationid")
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<CoversationModel> getAllConversationID(@QueryParam("userid") String userID)
    {
        System.out.println("YOYO BOIZS");
        List<Long> conversationids = em.createNativeQuery("SELECT cc.CONVERSATIONS_ID FROM Contact_Conversation cc Where cc.PARTS_ID = '"+userID+"'").getResultList();
        List<Conversation> conversations = em.createQuery("Select c From Conversation c").getResultList();
        System.out.println("YOYO BO222IZS");
        List<CoversationModel> returnList = new ArrayList<>();
        
        System.out.println("HALLOO");
        if(conversationids == null) {
            return null;
        }
        if(conversationids.size() == 0) {
                return null;
            }
        System.out.println("FORREA;!!!!!!!!!!!!!!!!");
        System.out.println("Soze_" + conversationids.size());
        for(int i = 0; i < conversationids.size(); i++)
        {
            
            System.out.println("FOR 1!!!!!!!!!!!!!!!!");
            for(int x = 0; x <conversations.size();x++)
            {
                if(conversations.get(x).getId().equals(conversationids.get(i)))
                {
                    List<Contact> parts = conversations.get(x).getParts();
                    String senderID = "";
                    String sender = null;
                    for(int y = 0; y < parts.size();y++)
                    {
                        Contact con = parts.get(y);
                        if(!con.getId().equals(userID))
                        {
                            senderID = con.getId();
                            sender = con.getUsername();
                        }
                    }
                    System.out.println("YOYO BO2212212IZS");
                    List<Message> mess =  em.createQuery("SELECT m from Message m where m.conversation.id = :conversationId").setParameter("conversationId",conversations.get(x).getId()).getResultList();
                    if(mess.size() <= 0) {
                        break;
                    }
                    Message message = mess.get(mess.size() - 1);
                    String lastMessage = message.getMessage();
                    CoversationModel cm = new CoversationModel(conversations.get(x).getId().toString(),sender, lastMessage, senderID);
                    returnList.add(cm);
                }
            }
            
        }
        
        return returnList;
    }
    
    //http://localhost:8080/MyChat/services/chat/getconversation?conversationid=1
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("getconversation")
    public List<Message> getConversationMessage(@QueryParam("conversationid") int conversationID)
    {
        List<Message> mess =  em.createQuery("SELECT m from Message m where m.conversation.id = :conversationId").setParameter("conversationId",conversationID).getResultList();
        return mess;
    }
            
    //SELECT m.ID , m.MESSAGE , m.CONVERSATION_ID from Contact_Conversation c INNER join Conversation k on c.CONVERSATIONS_ID = k.ID Inner join Message m on m.CONVERSATION_ID = c.CONVERSATIONS_ID where c.PARTS_ID = '2121' 
    
    //http://localhost:8080/MyChat/services/chat/createconversation?fromID=sdsd23&toID=2123
    @GET
    @Produces(value = MediaType.TEXT_PLAIN)
    @Path("createconversation")
    public Long createConversation(@QueryParam("fromID") String fromID, @QueryParam("toID") String toID)
    {
        long returnValue = doesConversationExsist(fromID,toID);
        long doesNotExsist = (long) -1;
        if(returnValue == doesNotExsist){
        List<Contact> fromList = em.createQuery("SELECT c From Contact c where c.id = :contactID").setParameter("contactID", fromID).getResultList();
        Contact fromContact = fromList.get(0);
        List<Contact> toList = em.createQuery("SELECT c From Contact c where c.id = :contactID").setParameter("contactID", toID).getResultList();
        Contact toContact = toList.get(0);
        
        Conversation conv = new Conversation(fromContact,toContact);
        fromContact.addConversation(conv);
        toContact.addConversation(conv);
        
        em.persist(conv);
        em.persist(fromContact);
        em.persist(toContact);
        returnValue =  conv.getId();
        }
        return returnValue;
    }
    
    @GET
    @Produces(value = MediaType.TEXT_PLAIN)
    @Path("havenewmessages")
    public boolean haveNewMessages(@QueryParam("userid") String userid){
        List<Contact> contacts = em.createQuery("SELECT c From Contact c where c.id = :contactID").setParameter("contactID", userid).getResultList();
        
       Contact recContact = contacts.get(0);
       boolean returnBoolean = recContact.isGotNewMessages();

       recContact.setGotNewMessages(false);
       em.persist(recContact);
        
        
        return returnBoolean;
    }
    
    
    @GET
    @Produces(value = MediaType.TEXT_PLAIN)
    @Path("getusername")
    public String getUsername(@QueryParam("userid") String userid){
        List<String> usernames = em.createNativeQuery("SELECT c.username From Contact c where c.id = '"+userid+"'").getResultList();
        String username = usernames.get(0);
        return username;
    }
    
    
    
    
    //http://localhost:8080/MyChat/services/chat/sendmessage?conversationid=1&senderid=1&messageid=1231234&message=hallais
    @GET
    @Produces(value = MediaType.TEXT_PLAIN)
    @Path("sendmessage")
    public String sendMessage(@QueryParam("conversationid") Long conversationID,@QueryParam("sender") String senderID, @QueryParam("messageid") String messageid, @QueryParam("message") String message)
    {
       List<Conversation> conversations = em.createQuery("SELECT c From Conversation c where c.id = :conversationID").setParameter("conversationID", conversationID).getResultList();
       Conversation currentConversation = conversations.get(0);
       
       List<Contact> parts = currentConversation.getParts();
       String recieverID = "";
       for(int i = 0; i < parts.size();i++)
       {
           if(!parts.get(i).getId().equals(senderID))
           {
               recieverID = parts.get(i).getId();
           }
       }
       System.out.println("**********");
        System.out.println("min id "  + senderID);
       List<Contact> recContacts = em.createQuery("SELECT c From Contact c where c.id = :contactID").setParameter("contactID", recieverID).getResultList();
       Contact recContact = recContacts.get(0);
       recContact.setGotNewMessages(true);
       em.persist(recContact);
       
       List<Contact> contacts = em.createQuery("SELECT c From Contact c where c.id = :contactID").setParameter("contactID", senderID).getResultList();
       Contact sender = contacts.get(0);
       Message newMessage = new Message(messageid,currentConversation,sender,message);
       currentConversation.addMessage(newMessage);
       em.persist(currentConversation);
       return "sent";
    }
    
    private Long doesConversationExsist(String fromID,String toID)
    {
        Long conversationID = (long) -1;
        List<Long> cidsOne = em.createNativeQuery("SELECT cc.CONVERSATIONS_ID FROM Contact_Conversation cc WHERE  cc.PARTS_ID = '"+fromID+"'").getResultList();
        List<Long> cidsTwo = em.createNativeQuery("SELECT cc.CONVERSATIONS_ID FROM Contact_Conversation cc WHERE  cc.PARTS_ID = '"+toID+"'").getResultList();

        for(int i = 0; i< cidsOne.size(); i++)
        {
            for(int y = 0; y < cidsTwo.size(); y++){
                if(Objects.equals(cidsOne.get(i), cidsTwo.get(y)))
                {
                    conversationID = cidsOne.get(i);
                }
            }
        }
        return conversationID;
    }
    
    
    
    //http://localhost:8080/MyChat/services/chat/adduser?name=Eirik
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("init")
    public Contact initDB()
    {
       Contact contactOne = new Contact("12345","DBuser");
       Contact contactTwo = new Contact("54321","DBuser2");    
       Conversation conv1 = new Conversation(contactOne,contactTwo);
       
       Message message = new Message("123123" ,conv1, contactOne, "Testmelding");
       conv1.addMessage(message);
       ArrayList<Conversation> conversation1 = new ArrayList<>();
       conversation1.add(conv1);
  
       contactOne.addConversation(conv1);
       contactTwo.addConversation(conv1);
       em.persist(contactOne);
       em.persist(contactTwo);

 

       return contactOne;
    }
    
    
}
