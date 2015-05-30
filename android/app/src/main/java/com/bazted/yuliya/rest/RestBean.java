/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.rest;

import com.bazted.yuliya.app.YApp;
import com.bazted.yuliya.rest.client.YuliyaRest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * ’ * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
@EBean
public class RestBean {

    private static final String AUTH_HEADER = "X-APP-Auth";
    private static final String ENDPOINT = "http://yuliyaweb.azurewebsites.net/api";
    @App
    YApp app;

    private YuliyaRest rest;
    private Gson converter;

    @AfterInject
    protected void start() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                if (app.auth().isLogged()) {
                    request.addHeader(AUTH_HEADER, app.auth().token());
                }
            }
        };

        converter = new GsonBuilder().create();
        this.rest = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setConverter(new GsonConverter(converter))
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build().create(YuliyaRest.class);

    }

    public YuliyaRest rest() {
        return rest;
    }

    public static String getTokenFromHeaders(Response response) {
        int size = response.getHeaders().size();
        Header header;
        for (int i = 0; i < size; i++) {
            header = response.getHeaders().get(i);
            if (header.getName().equals(AUTH_HEADER)) {
                return header.getValue();
            }
        }
        return null;
    }

    public Gson converter() {
        return converter;
    }
}
