package com.absut.jetquotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    val author: String,
    val content: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val length: Int,
  //  var isFavorite: Boolean = false
)
