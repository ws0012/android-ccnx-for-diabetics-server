/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gson.util;
import com.google.gson.JsonArray;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pl.lecznica.Pacjent;
import org.pl.lecznica.Wynik;
import org.pl.lecznica.messages.GetMessage;
import org.pl.lecznica.messages.MessageBody;
import org.pl.lecznica.messages.DocHealthInfoMessage;
import org.pl.lecznica.messages.HealthInfoMessage;
import org.pl.lecznica.messages.StringMessage;
/**
 *
 * @author Wojtek
 */
public class MessageBodyDeserializer implements JsonDeserializer<MessageBody> {

  @Override
  public MessageBody deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext jdc)
      throws JsonParseException {
    final JsonObject jsonObject = json.getAsJsonObject();
    MessageBody message = null;
    if(jsonObject.get("text_message")!=null) {
        message = new StringMessage();
        ((StringMessage)message).setTextMessage(jsonObject.get("text_message").getAsString());
    } else if(jsonObject.get("date_from")!=null) {
        message = new GetMessage();
        String data = jsonObject.get("date_from").getAsString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	formatter.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        try {
            Date dataFrom = new Date(formatter.parse(data).getTime());
            ((GetMessage)message).setDateFrom(dataFrom);
        } catch (ParseException ex) {
            Logger.getLogger(MessageBodyDeserializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else if(jsonObject.get("healthInfo")!=null) {
        List<Wynik> healthInfo = new ArrayList<Wynik>();
        message = new HealthInfoMessage();
        JsonArray healthInfoArray = jsonObject.get("healthInfo").getAsJsonArray();
        for(JsonElement js : healthInfoArray){
            Wynik wynik = jdc.deserialize(js, Wynik.class);
            healthInfo.add(wynik);
        }
        ((HealthInfoMessage)message).setHealthInfo(healthInfo);
        
    } else if(jsonObject.get("patientsInfo")!=null) {
        List<Pacjent> patientsInfo = new ArrayList<Pacjent>();
        message = new DocHealthInfoMessage();
        JsonArray patientsInfoArray = jsonObject.get("patientsInfo").getAsJsonArray();
        for(JsonElement js : patientsInfoArray){
            Pacjent pacjent = jdc.deserialize(js, Pacjent.class);
            patientsInfo.add(pacjent);
        }
        ((DocHealthInfoMessage)message).setPatientsInfo(patientsInfo);
        
    }else throw new JsonParseException("Not supported json!");
    
    if(message != null)
        message.setKey(jsonObject.get("key").getAsString());      
    return message;  
  }

}

