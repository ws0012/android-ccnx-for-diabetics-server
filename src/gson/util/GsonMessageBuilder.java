/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gson.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.pl.lecznica.Pacjent;
import org.pl.lecznica.Wynik;
import org.pl.lecznica.messages.GetMessage;
import org.pl.lecznica.messages.Message;
import org.pl.lecznica.messages.MessageBody;
import org.pl.lecznica.messages.StringMessage;

/**
 *
 * @author Wojtek
 */
public class GsonMessageBuilder {
    private final GsonBuilder gsonBuilder;
    private final Gson gson;
    
    public GsonMessageBuilder () {
        gsonBuilder = new GsonBuilder();
//        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
        gsonBuilder.registerTypeAdapter(StringMessage.class, new StringMessageSerializer());
        gsonBuilder.registerTypeAdapter(GetMessage.class, new GetMessageSerializer());
        gsonBuilder.registerTypeAdapter(MessageBody.class, new MessageBodyDeserializer());
        gsonBuilder.registerTypeAdapter(Wynik.class, new WynikAdapter());
        gsonBuilder.registerTypeAdapter(Pacjent.class, new PacjentAdapter());
        
        gson = gsonBuilder.create();
    }
    
    public Message fromJson(String gsonString){

        return gson.fromJson(gsonString, Message.class);
    }
    
    public String toJson(Message message){

        return gson.toJson(message);
    }
}
