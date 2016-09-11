/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.app.nfc;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
public class BusNfc {

    /**
     * BusId : 735
     */
    private int BusId;

    public void setBusId(int BusId) {
        this.BusId = BusId;
    }

    public int getBusId() {
        return BusId;
    }

    @Override
    public String toString() {
        return "BusNfc{" +
                "BusId=" + BusId +
                '}';
    }
}
