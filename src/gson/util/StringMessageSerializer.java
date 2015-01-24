/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gson.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.pl.lecznica.messages.Message;
import org.pl.lecznica.messages.StringMessage;

/**
 *
 * @author Wojtek
 */
public class StringMessageSerializer implements JsonSerializer<StringMessage>  {

    @Override
    public JsonElement serialize(StringMessage t, Type type, JsonSerializationContext jsc) {

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("key", t.getKey());
    jsonObject.addProperty("text_message", t.getTextMessage());
    
    return jsonObject;
    }
    
}
