/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.rest.request;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
public class BuyTicketReq {

    public BuyTicketReq(int busNumber, int typeId) {
        BusNumber = busNumber;
        Type = new TypeEntity();
        Type.setId(typeId);
    }

    /**
     * Type : {"Id":1}
     */

    private int BusNumber;

    private TypeEntity Type;

    public TypeEntity getType() {
        return Type;
    }

    public void setId(int id) {
        this.Type = new TypeEntity();
        this.Type.setId(id);
    }

    public class TypeEntity {
        /**
         * Id : 1
         */
        private int Id;

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getId() {
            return Id;
        }
    }
}
