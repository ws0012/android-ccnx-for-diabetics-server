/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pl.lecznica.messages;

/**
 *
 * @author Wojtek
 */
public class Message {
    private String name;
    private MessageBody messageBody;
    
    public Message () {}
    public Message (String _name){
        name = _name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the messageBody
     */
    public MessageBody getMessageBody() {
        return messageBody;
    }

    /**
     * @param messageBody the messageBody to set
     */
    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }


    
}
