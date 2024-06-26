package com.github.parking.smartparking.home.data.remote

import com.github.parking.smartparking.home.domain.model.AccessToken
import com.github.parking.smartparking.home.domain.model.STKPush
import com.github.parking.smartparking.home.domain.model.STKPushQuery
import com.github.parking.smartparking.home.domain.model.STKPushQueryResponse
import com.github.parking.smartparking.home.domain.model.STKPushResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface STKPushService {

    @GET("jobs/pending")
    fun tasks(): Call<STKPush>

    @GET("oauth/v1/generate?grant_type=client_credentials")
    fun accessToken(): Call<AccessToken>

    @POST("mpesa/stkpush/v1/processrequest")
    fun sendPush(@Body stkPush: STKPush): Call<STKPushResponse>


    @POST("mpesa/stkpushquery/v1/query")
    fun queryPush(@Body stkPushQuery: STKPushQuery):Call<STKPushQueryResponse>


}