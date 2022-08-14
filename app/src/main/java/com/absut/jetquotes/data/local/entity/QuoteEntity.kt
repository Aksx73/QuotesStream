package com.absut.jetquotes.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quotes")
data class QuoteEntity(
    val author: String,
  //  val authorSlug: String,
    val content: String,
   //val dateAdded: String,
   // val dateModified: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val length: Int,
    var isFavorite: Boolean = false
   // val tags: List<String>
)
