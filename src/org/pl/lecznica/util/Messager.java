/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pl.lecznica.util;

import com.google.gson.JsonSyntaxException;
import gson.util.GsonMessageBuilder;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pl.lecznica.Pacjent;
import org.pl.lecznica.Wynik;
import org.pl.lecznica.messages.Message;
import org.pl.lecznica.messages.MessageFactory;

/**
 *
 * @author Wojtek
 */
public class Messager {
    private GsonMessageBuilder gsonBuilder;
    private MessageFactory messageFactory;
    
    public Messager (){
        messageFactory = new MessageFactory();
        gsonBuilder = new GsonMessageBuilder(); 
    }
    
    public String toJson (Message message){
        return gsonBuilder.toJson(message);
    }
    
    public Message fromJson (String json){
        try {
            return gsonBuilder.fromJson(json);     
        } catch (JsonSyntaxException ex){
            return null;
        } catch (Exception ex) {
            Logger.getLogger(Lecznica.class.getName()).log(Level.SEVERE, null, ex);
        } return null; 
    }
    
    public String getStringMessage(String name, String flag){
        Message message = messageFactory.createStringMessage(name, flag);
        return toJson(message);
    }
    
    public String getGetMessage(String name, Date dateFrom){
        Message message = messageFactory.createGetMessage(name, dateFrom);
        return toJson(message);
    }
 
    public String getHealthInfoMessage(String name, List<Wynik> _healthInfo){
        Message message = messageFactory.createHealthInfoMessage(name, _healthInfo);
        return toJson(message);
    }
    
    public String getDocHealthInfoMessage(String name, List<Pacjent> _patientsInfo){
        Message message = messageFactory.createDocHealthInfoMessage(name, _patientsInfo);
        return toJson(message);
    }
}
