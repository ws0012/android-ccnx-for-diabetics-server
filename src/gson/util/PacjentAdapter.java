/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gson.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.pl.lecznica.Pacjent;
import org.pl.lecznica.Wynik;

/**
 *
 * @author Wojtek
 */
public class PacjentAdapter implements JsonSerializer<Pacjent>, JsonDeserializer<Pacjent>  {

    @Override
    public JsonElement serialize(Pacjent t, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("imie", t.getImie());
        jsonObject.addProperty("nazwisko", t.getNazwisko());
        jsonObject.addProperty("pesel", t.getPesel());
        final JsonElement wyniki = jsc.serialize(t.getWyniki());
        jsonObject.add("wyniki", wyniki);

        return jsonObject;
    }

    @Override
    public Pacjent deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        final JsonObject jsonObject = je.getAsJsonObject();
        Pacjent pacjent = new Pacjent();
        pacjent.setImie(jsonObject.get("imie").getAsString());
        pacjent.setPesel(jsonObject.get("pesel").getAsString());
        JsonArray wynikiArray = jsonObject.get("wyniki").getAsJsonArray();
        for(JsonElement js : wynikiArray){
            Wynik wynik = jdc.deserialize(js, Wynik.class);
            pacjent.getWyniki().add(wynik);
        }
        
        return pacjent;
    }
    
    
}
