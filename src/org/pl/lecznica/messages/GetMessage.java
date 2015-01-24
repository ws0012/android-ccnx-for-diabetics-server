/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pl.lecznica.messages;

import java.sql.Date;

/**
 *
 * @author Wojtek
 */
public class GetMessage extends MessageBody {
    private Date dateFrom;
    
    public GetMessage(){}
    public GetMessage(Date _dateFrom){
        dateFrom = _dateFrom;
    }

    /**
     * @return the dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom the dateFrom to set
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public String toString() {
        return "GetMessage{"+ super.toString() + ", dateFrom=" + dateFrom + '}';
    }
    
    
    
}
