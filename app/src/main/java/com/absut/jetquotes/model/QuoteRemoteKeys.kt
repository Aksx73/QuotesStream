package com.absut.jetquotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuoteRemoteKeys")
data class QuoteRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
