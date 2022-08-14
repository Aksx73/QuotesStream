package com.absut.jetquotes.domain.model

import com.google.gson.annotations.SerializedName

data class Quote(
    val author: String,
    val content: String,
    val id: String,
    val length: Int,
    var isFavorite: Boolean = false
)
