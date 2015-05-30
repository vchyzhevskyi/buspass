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
import retrofit.client.Header;
import retrofit.client.Response;

/**
 * Created by T.Bazyshyn on 30/05/15.
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

        this.rest = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(requestInterceptor)
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
}
