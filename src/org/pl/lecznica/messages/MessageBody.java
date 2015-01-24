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
public class MessageBody {
    
    private String key;

    public MessageBody () {
        key = "XXXXXXXXXXXXXX";
    }
    public MessageBody (String _key){
        key = _key;
    }
    
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    public String toString() {
        return "key: "+key;
    }
    
}
