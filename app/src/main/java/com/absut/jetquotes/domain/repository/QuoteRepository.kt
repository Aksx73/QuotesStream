package com.absut.jetquotes.domain.repository

import androidx.paging.PagingData
import com.absut.jetquotes.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    fun getQuotes(): Flow<PagingData<Quote>>

    suspend fun addRemoveFromFavorite(id: String, isFavorite: Boolean)

    fun getFavoriteQuotes(): Flow<List<Quote>>

}