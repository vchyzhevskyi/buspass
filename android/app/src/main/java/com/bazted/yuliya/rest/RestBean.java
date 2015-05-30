/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.rest;

import com.bazted.yuliya.app.YApp;
import com.bazted.yuliya.rest.client.YuliyaRest;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
@EBean
public class RestBean {

    @App
    YApp app;

    private YuliyaRest rest;

    @AfterInject
    protected void start() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                if (app.auth().isLogged()) {
                    request.addHeader("X-APP-Auth", app.auth().token());
                }
            }
        };

        this.rest = new RestAdapter.Builder()
                .setEndpoint("http://yuliyaweb.azurewebsites.net/api")
                .setRequestInterceptor(requestInterceptor)
                .build().create(YuliyaRest.class);

    }

    public YuliyaRest rest() {
        return rest;
    }
}
