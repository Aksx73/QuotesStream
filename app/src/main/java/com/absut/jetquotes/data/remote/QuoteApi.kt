package com.absut.jetquotes.data.remote

import com.absut.jetquotes.data.remote.dto.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    @GET("/quotes")
    suspend fun getQuotes(@Query("page") pageNum: Int): ApiResponse

    //TODO getQuotesByTags, getRandomQuote, getFamousQuotes, getQuotesByAuthor

}