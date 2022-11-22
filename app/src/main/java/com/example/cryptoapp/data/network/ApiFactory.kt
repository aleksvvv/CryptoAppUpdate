package com.example.cryptoapp.data.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private val BASEURL = "https://min-api.cryptocompare.com/data/"

    private val retrofit = Retrofit.Builder().
    addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASEURL)
        .build()
    val apiService = retrofit.create(ApiService::class.java)

}