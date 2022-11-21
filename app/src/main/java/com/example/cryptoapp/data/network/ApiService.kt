package com.example.cryptoapp.data.network

import com.example.cryptoapp.data.network.model.CoinNamesListDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
   @GET("top/totalvolfull")
    suspend fun getTopCoinsInfo(
       @Query(QUERY_PARAM_API_KEY) apiKey: String = "9342a855c19f8d252758c60083a523dba785b617f69acb96b04b0946aaf87c0b",
       @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
       @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): CoinNamesListDto

    @GET("pricemultifull")
    suspend fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "9342a855c19f8d252758c60083a523dba785b617f69acb96b04b0946aaf87c0b",
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY,
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String
    ): CoinInfoJsonContainerDto
    companion object {
        private const val CURRENCY = "USD"
        private const val QUERY_PARAM_API_KEY = ""
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

    }
}