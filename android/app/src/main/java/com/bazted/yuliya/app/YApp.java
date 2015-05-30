/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.app;

import android.app.Application;

import com.bazted.yuliya.app.auth.AuthManager;
import com.bazted.yuliya.rest.RestBean;
import com.bazted.yuliya.rest.client.YuliyaRest;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;

/**
 * Created by T.Bazyshyn on 29/05/15.
 *
 * @author T.Bazyshyn
 * @since 29/05/15
 */
@EApplication
public class YApp extends Application {

    @Bean
    AuthManager authManager;

    @Bean
    RestBean restBean;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public AuthManager auth() {
        return authManager;
    }

    public YuliyaRest api() {
        return restBean.rest();
    }
}
