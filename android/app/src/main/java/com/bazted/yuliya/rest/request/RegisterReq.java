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
public class RegisterReq {

    /**
     * Login : sample string 1
     * Password : sample string 2
     */
    private String Login;
    private String Password;

    public void setLogin(String Login) {
        this.Login = Login;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getLogin() {
        return Login;
    }

    public String getPassword() {
        return Password;
    }
}
