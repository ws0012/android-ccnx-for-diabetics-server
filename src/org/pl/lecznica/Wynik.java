/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pl.lecznica;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Wojtek
 */
public class Wynik {
    private long id;
    private long idPacjent;
    private String cisnienie;
    private String temperatura;
    private String poziomCukru;
    private Date dataBadania;
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the cisnienie
     */
    public String getCisnienie() {
        return cisnienie;
    }

    /**
     * @param cisnienie the cisnienie to set
     */
    public void setCisnienie(String cisnienie) {
        this.cisnienie = cisnienie;
    }

    /**
     * @return the temperatura
     */
    public String getTemperatura() {
        return temperatura;
    }

    /**
     * @param temperatura the temperatura to set
     */
    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * @return the dataBadania
     */
    public Date getDataBadania() {
        return dataBadania;
    }

    /**
     * @param dataBadania the dataBadania to set
     */
    public void setDataBadania(Date dataBadania) {
        this.dataBadania = dataBadania;
    }

    /**
     * @return the poziomCukru
     */
    public String getPoziomCukru() {
        return poziomCukru;
    }

    /**
     * @param poziomCukru the poziomCukru to set
     */
    public void setPoziomCukru(String poziomCukru) {
        this.poziomCukru = poziomCukru;
    }

    @Override
    public String toString() {
        return "Wynik{" + "cisnienie=" + cisnienie + ", temperatura=" + temperatura + ", poziomCukru=" + poziomCukru + ", dataBadania=" + dataBadania + '}';
    }

    /**
     * @return the idPacjent
     */
    public long getIdPacjent() {
        return idPacjent;
    }

    /**
     * @param idPacjent the idPacjent to set
     */
    public void setIdPacjent(long idPacjent) {
        this.idPacjent = idPacjent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (int) (this.idPacjent ^ (this.idPacjent >>> 32));
        hash = 13 * hash + Objects.hashCode(this.dataBadania);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Wynik other = (Wynik) obj;
        if (this.idPacjent != other.idPacjent) {
            return false;
        }
        if (!Objects.equals(this.dataBadania, other.dataBadania)) {
            return false;
        }
        return true;
    }
    
}
