package com.absut.jetquotes.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("lastItemIndex")
    val lastItemIndex: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("quoteDtos")
    val quoteDtos: List<QuoteDto>,
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)