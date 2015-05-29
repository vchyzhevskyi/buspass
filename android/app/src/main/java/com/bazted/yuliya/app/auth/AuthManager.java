/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.app.auth;

import android.text.TextUtils;

import com.bazted.yuliya.app.security.Sha1;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.api.sharedpreferences.StringPrefField;

/**
 * Created by T.Bazyshyn on 29/05/15.
 *
 * @author T.Bazyshyn
 * @since 29/05/15
 */
@EBean
public class AuthManager {

    @Pref
    AuthPref_ authPref;

    public boolean isLogged() {
        return !TextUtils.isEmpty(token());
    }

    private StringPrefField tokenField() {
        return authPref.token();
    }

    public String token() {
        return tokenField().get();
    }

    private StringPrefField emailField() {
        return authPref.token();
    }

    public String email() {
        return emailField().get();
    }

    private StringPrefField pinHashField() {
        return authPref.pinHash();
    }

    private String pin() {
        return pinHashField().get();
    }

    public void setPin(String pin) {
        pinHashField().put(Sha1.getHash(pin));
    }

    public boolean matchPin(String pin) {
        return pin().equals(Sha1.getHash(pin));
    }

    public void login(String email, String token) {
        if (TextUtils.isEmpty(email)) {
            throw new IllegalStateException("email should not be empty");
        }
        if (TextUtils.isEmpty(token)) {
            throw new IllegalStateException("token should not be empty");
        }
        String substring = email.substring(0, email.indexOf('@'));
        emailField().put(substring);
        tokenField().put(token);
    }

    public void logout() {
        emailField().put(null);
        tokenField().put(null);
        pinHashField().put(null);
    }

}
