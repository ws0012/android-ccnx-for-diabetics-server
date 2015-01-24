/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gson.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.pl.lecznica.messages.Message;
import org.pl.lecznica.messages.MessageBody;

/**
 *
 * @author Wojtek
 */
public class MessageAdapter implements JsonSerializer<Message>, JsonDeserializer<Message>  {

    @Override
    public Message deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {

        final JsonObject jsonObject = je.getAsJsonObject();
        final String name = jsonObject.get("name").getAsString();
        final MessageBody body = jdc.deserialize(jsonObject.get("message_body"), MessageBody.class);
        
        Message message = new Message();
        message.setName(name);
        message.setMessageBody(body);
        
        return message;
    }
    
    @Override
    public JsonElement serialize(Message t, Type type, JsonSerializationContext jsc) {

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("name", t.getName());
    JsonElement messageBody = jsc.serialize(t.getMessageBody());
    jsonObject.add("message_body", messageBody);
    
    return jsonObject;
    }
}
