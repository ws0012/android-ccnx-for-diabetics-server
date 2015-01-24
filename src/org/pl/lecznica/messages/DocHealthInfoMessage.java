/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pl.lecznica.messages;

import java.util.ArrayList;
import java.util.List;
import org.pl.lecznica.Pacjent;

/**
 *
 * @author Wojtek
 */
public class DocHealthInfoMessage extends MessageBody{
    private List<Pacjent> patientsInfo = new ArrayList<Pacjent>();
    
    public DocHealthInfoMessage () {}
    public DocHealthInfoMessage (List<Pacjent> _patientsInfo) {
        patientsInfo = _patientsInfo;
    }
    

    /**
     * @return the patientsInfo
     */
    public List<Pacjent> getPatientsInfo() {
        return patientsInfo;
    }

    /**
     * @param patientsInfo the patientsInfo to set
     */
    public void setPatientsInfo(List<Pacjent> patientsInfo) {
        this.patientsInfo = patientsInfo;
    }

    @Override
    public String toString() {
        String result = "PutDocHealthInfoMessage{"+super.toString()+", patientsInfo:{" ;
        for(Pacjent pacjent : patientsInfo) {
          result += pacjent.toString();
        }
        result +="}}"; 
        return result;
    }


    
    
}
