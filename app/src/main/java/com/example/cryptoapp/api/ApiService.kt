package com.example.cryptoapp.api

import com.example.cryptoapp.pojo.CoinInfoListOfData
import com.example.cryptoapp.pojo.CoinPriceInfoRawData
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
   @GET("top/totalvolfull")
    fun getTopCoinsInfo(
     @Query(QUERY_PARAM_API_KEY) apiKey: String = "9342a855c19f8d252758c60083a523dba785b617f69acb96b04b0946aaf87c0b",
     @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
     @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "9342a855c19f8d252758c60083a523dba785b617f69acb96b04b0946aaf87c0b",
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY,
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String
    ): Single<CoinPriceInfoRawData>
    companion object {
        private const val CURRENCY = "USD"
        private const val QUERY_PARAM_API_KEY = ""
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

    }
}