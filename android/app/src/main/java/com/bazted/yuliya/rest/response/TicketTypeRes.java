/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.rest.response;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
public class TicketTypeRes {

    /**
     * Cost : 3.0
     * Name : sample string 2
     * Id : 1
     */
    private double Cost;
    private String Name;
    private int Id;

    public void setCost(double Cost) {
        this.Cost = Cost;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public double getCost() {
        return Cost;
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return Id;
    }
}
