/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pl.lecznica.util;

import ccnxserver.CCNRestrictedServer;
import java.io.IOException;
import java.sql.Date;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;
import org.pl.lecznica.Lekarz;
import org.pl.lecznica.Pacjent;
import org.pl.lecznica.Uzytkownik;
import org.pl.lecznica.Wynik;
import org.pl.lecznica.dao.LecznicaDaoImp;
import org.pl.lecznica.messages.DocHealthInfoMessage;
import org.pl.lecznica.messages.GetMessage;
import org.pl.lecznica.messages.HealthInfoMessage;
import org.pl.lecznica.messages.Message;
import org.pl.lecznica.messages.MessageBody;
import org.pl.lecznica.messages.StringMessage;

/**
 *
 * @author Wojtek
 */
public class Lecznica {

    protected static Map<String,CCNRestrictedServer> serverChats;
    public static Messager messager;
    protected static LecznicaDaoImp dao;
    
    public Lecznica(){
        dao = new LecznicaDaoImp();
        serverChats =  new HashMap<String,CCNRestrictedServer>();
        messager = new Messager();
    }
    
    public CCNRestrictedServer getServerServer4Name(String name){
        return serverChats.get(name);
    }
    
    public CCNRestrictedServer putRestrictedServer (String name, CCNRestrictedServer chat){
        CCNRestrictedServer server = getServerServer4Name(name);
        if(server != null) return server; 
        else return serverChats.put(name, chat);
    }
    
    public CCNRestrictedServer removeRestrictedServer (String name) {
        return serverChats.remove(name);
    }    
        
    public String getMessageName(String message){
        Message recMessage = messager.fromJson(message);
        if(recMessage != null) return recMessage.getName();
        else return null;
    }
    


    public Uzytkownik getUser2Name(String name) throws Exception {
        List<Uzytkownik> uzytkownicy = dao.get2Property(Uzytkownik.class, "nazwa", name);
        if(uzytkownicy != null){
            if(uzytkownicy.size()<1) return null;
            else if(uzytkownicy.size()>1) throw new Exception("Błąd. Wielu uzytkowników z tą samą nazwą!!!");
            else {
                return uzytkownicy.get(0);                
            }
        }
        return null;
    }
    

    
    public String processMessage(String message) throws Exception{
        Message recMessage = messager.fromJson(message);
        if(recMessage != null) {
            Uzytkownik uzytkownik = getUser2Name(recMessage.getName());
            if(uzytkownik==null) return null;

            MessageBody mb = recMessage.getMessageBody();
            if(StringMessage.class.equals(mb.getClass()))
                return processMessage((StringMessage)mb);
            else if(GetMessage.class.equals(mb.getClass()))
                return processMessage(uzytkownik,(GetMessage)mb);
            else if(HealthInfoMessage.class.equals(mb.getClass()))
                return processMessage(uzytkownik,(HealthInfoMessage)mb);
            else if(DocHealthInfoMessage.class.equals(mb.getClass()))
                return processMessage((DocHealthInfoMessage)mb);
            else throw new IllegalArgumentException ("Unsupported type of: "+ mb.getClass());
        } 
        return null;
    }
    
    public String processMessage(StringMessage message){
        String result = message.getTextMessage();
        return result;
    }
    
    public String processMessage(Uzytkownik uzytkownik, GetMessage message){
        
        if(uzytkownik instanceof Pacjent){
            Message resultMessage = new Message(uzytkownik.getNazwa());
            resultMessage.setMessageBody(new HealthInfoMessage(((Pacjent)uzytkownik).getWyniki()));
            return messager.toJson(resultMessage);
        }
        if(uzytkownik instanceof Lekarz){
            Message resultMessage = new Message(uzytkownik.getNazwa());
            resultMessage.setMessageBody(new DocHealthInfoMessage(((Lekarz)uzytkownik).getPacjenci()));
            return messager.toJson(resultMessage);
        }

        return null;
    }

    public String processMessage(Uzytkownik uzytkownik, HealthInfoMessage message){

        if(uzytkownik instanceof Pacjent){
            List<Wynik> wyniki = ((Pacjent)uzytkownik).getWyniki();
            for(Wynik wynik : message.getHealthInfo()){
                System.out.println(wynik);
                if(!(wyniki.contains(wynik)))
                    wyniki.add(wynik);               
            }
            
        dao.save(uzytkownik);
        }
        
        return "DONE";
    }

    public String processMessage(DocHealthInfoMessage message){
        String result = null;
        return result;
    }

}
