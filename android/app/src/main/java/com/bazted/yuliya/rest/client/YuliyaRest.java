/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.rest.client;

import com.bazted.yuliya.rest.request.BuyTicketReq;
import com.bazted.yuliya.rest.request.RegisterReq;
import com.bazted.yuliya.rest.response.TicketRes;
import com.bazted.yuliya.rest.response.TicketTypeRes;

import java.util.List;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
public interface YuliyaRest {
    @GET("/User?login={login}&password={password}")
    Response login(@Path("login") String login, @Path("password") String pass);

    @POST("/User")
    Response register(@Body RegisterReq registerReq);

    @GET("/TicketType")
    List<TicketTypeRes> getListOfTicketTypes();

    @POST("/Ticket")
    Response buyTicket(@Body BuyTicketReq buyTicketReq);

    @GET("/Ticket")
    List<TicketRes> getTicketHistory();

}
