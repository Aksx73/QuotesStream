package com.absut.jetquotes.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.absut.jetquotes.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): PagingSource<Int,Quote>

    @Query("DELETE FROM quotes")
    suspend fun clearAllQuotes()

    @Query("UPDATE quotes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    @Query("SELECT * FROM quotes WHERE isFavorite = 1")
    fun getAllFavoriteQuotes(): Flow<List<Quote>>

}