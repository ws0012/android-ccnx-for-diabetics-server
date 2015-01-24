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
public class StringMessage extends MessageBody{
    private String textMessage;

    public StringMessage(){}
    public StringMessage(String message){
        textMessage = message;
    }
    /**
     * @return the textMessage
     */
    public String getTextMessage() {
        return textMessage;
    }

    /**
     * @param textMessage the textMessage to set
     */
    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    @Override
    public String toString() {
        return "StringMessage{" + super.toString() + ", textMessage=" + textMessage + '}';
    }
    
    
}
