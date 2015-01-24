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
import org.pl.lecznica.messages.GetMessage;
import org.pl.lecznica.messages.StringMessage;

/**
 *
 * @author Wojtek
 */

public class GetMessageSerializer implements JsonSerializer<GetMessage>  {

    @Override
    public JsonElement serialize(GetMessage t, Type type, JsonSerializationContext jsc) {

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("key", t.getKey());
    jsonObject.addProperty("date_from", t.getDateFrom().toString());
    
    return jsonObject;
    }
    
}
    

