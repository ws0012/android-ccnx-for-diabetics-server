/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pl.lecznica;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wojtek
 */
public class Lekarz extends Uzytkownik{
    
    private List<Pacjent> pacjenci = new ArrayList<Pacjent>();

    /**
     * @return the pacjenci
     */
    public List<Pacjent> getPacjenci() {
        return pacjenci;
    }

    /**
     * @param pacjenci the pacjenci to set
     */
    public void setPacjenci(List<Pacjent> pacjenci) {
        this.pacjenci = pacjenci;
    }
    
    
}
