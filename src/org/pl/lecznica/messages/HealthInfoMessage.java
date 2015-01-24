/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pl.lecznica.messages;

import java.util.ArrayList;
import java.util.List;
import org.pl.lecznica.Wynik;

/**
 *
 * @author Wojtek
 */
public class HealthInfoMessage extends MessageBody{
    
    private List<Wynik> healthInfo = new ArrayList<Wynik>();

    public HealthInfoMessage () {}
    public HealthInfoMessage (List<Wynik> _healthInfo) {
        healthInfo = _healthInfo;
    }
    
    /**
     * @return the healthInfo
     */
    public List<Wynik> getHealthInfo() {
        return healthInfo;
    }

    /**
     * @param healthInfo the healthInfo to set
     */
    public void setHealthInfo(List<Wynik> healthInfo) {
        this.healthInfo = healthInfo;
    }

    @Override
    public String toString() {
        String result = "PutHealthInfoMessage{"+super.toString()+", healthInfo:{" ;
        for(Wynik wynik : healthInfo) {
          result += wynik.toString();
        }
        result +="}}";
        return  result;
    }
    
    
    
    
}
