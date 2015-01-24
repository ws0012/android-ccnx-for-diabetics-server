/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pl.lecznica.messages;

import java.sql.Date;
import java.util.List;
import org.pl.lecznica.Pacjent;
import org.pl.lecznica.Wynik;

/**
 *
 * @author Wojtek
 */
public class MessageFactory {
    
    public static final String INIT_MESSAGE = "INIT";
    public static final String READY_MESSAGE = "READY";
    public static final String CLOSE_MESSAGE = "CLOSE";
    public static final String DONE_MESSAGE = "DONE";
    
    private Message createMessage(String name) {
        return new Message(name);
    }
    
    public Message createStringMessage (String name, String message){
        if(!(message.equals(INIT_MESSAGE) || 
           message.equals(READY_MESSAGE) ||
           message.equals(CLOSE_MESSAGE))) throw new IllegalArgumentException("Unsupported StringMessage!");
        Message msg = createMessage(name);
        msg.setMessageBody(new StringMessage(message));
        return msg;
    }
    
    public Message createGetMessage (String name, Date dateFrom){
        Message msg = createMessage(name);
        msg.setMessageBody(new GetMessage(dateFrom));
        return msg;
    }
    
    public Message createHealthInfoMessage (String name, List<Wynik> _healthInfo){
        Message msg = createMessage(name);
        msg.setMessageBody(new HealthInfoMessage(_healthInfo));
        return msg;
    }

    public Message createDocHealthInfoMessage (String name, List<Pacjent> _patientsInfo){
        Message msg = createMessage(name);
        msg.setMessageBody(new DocHealthInfoMessage(_patientsInfo));
        return msg;
    }
}
