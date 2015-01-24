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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pl.lecznica.Wynik;
import org.pl.lecznica.messages.Message;

/**
 *
 * @author Wojtek
 */
public class WynikAdapter implements JsonSerializer<Wynik>, JsonDeserializer<Wynik>  {

    @Override
    public JsonElement serialize(Wynik t, Type type, JsonSerializationContext jsc) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("cisnienie", t.getCisnienie());
    jsonObject.addProperty("temperatura", t.getTemperatura());
    jsonObject.addProperty("poziomCukru", t.getPoziomCukru());
    jsonObject.addProperty("dataBadania", t.getDataBadania().toString());
            
    return jsonObject;    
    }
    @Override
    public Wynik deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
    final JsonObject jsonObject = je.getAsJsonObject();
    Wynik wynik = new Wynik();
    wynik.setCisnienie(jsonObject.get("cisnienie").getAsString());
    wynik.setTemperatura(jsonObject.get("temperatura").getAsString());
    wynik.setPoziomCukru(jsonObject.get("poziomCukru").getAsString());
    String data = jsonObject.get("dataBadania").getAsString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	formatter.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        try {
            Date dataBadania = new Date(formatter.parse(data).getTime());
            wynik.setDataBadania(dataBadania);
        } catch (ParseException ex) {
            Logger.getLogger(WynikAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    return wynik;
    }
    
    
}
