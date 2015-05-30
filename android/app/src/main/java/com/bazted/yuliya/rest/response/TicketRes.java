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
public class TicketRes {

    /**
     * Type : {"Cost":3,"Name":"sample string 2","Id":1}
     * Id : f5a9a1f9-62be-4aba-85c2-633b6e2bceb4
     * Bought : 2015-05-30T01:23:20.9213351+00:00
     */
    private TypeEntity Type;
    private String Id;
    private String Bought;


    public void setType(TypeEntity Type) {
        this.Type = Type;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setBought(String Bought) {
        this.Bought = Bought;
    }


    public TypeEntity getType() {
        return Type;
    }

    public String getId() {
        return Id;
    }

    public String getBought() {
        return Bought;
    }

    public class TypeEntity {
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
}
