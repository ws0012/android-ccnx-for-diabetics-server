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
public class Pacjent extends Uzytkownik{
   
    //dane osoby
    private String imie;
    private String nazwisko;
    private String pesel;
    private List<Wynik> wyniki = new ArrayList<Wynik>();

    /**
     * @return the imie
     */
    public String getImie() {
        return imie;
    }

    /**
     * @param imie the imie to set
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    /**
     * @return the nazwisko
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * @param nazwisko the nazwisko to set
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    /**
     * @return the pesel
     */
    public String getPesel() {
        return pesel;
    }

    /**
     * @param pesel the pesel to set
     */
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    /**
     * @return the wyniki
     */
    public List<Wynik> getWyniki() {
        return wyniki;
    }

    /**
     * @param wyniki the wyniki to set
     */
    public void setWyniki(List<Wynik> wyniki) {
        this.wyniki = wyniki;
    }

    @Override
    public String toString() {
        String result = "Pacjent{" + "imie=" + imie + ", nazwisko=" + nazwisko + ", pesel=" + pesel +"{";
        for(Wynik wynik : wyniki){
            result+=wynik.toString();
        }
        result+="}}";
        return  result;
    }


}
