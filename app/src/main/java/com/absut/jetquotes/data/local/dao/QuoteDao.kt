package com.absut.jetquotes.data.local.dao

import androidx.room.*
import com.absut.jetquotes.data.local.entity.QuoteEntity
import com.absut.jetquotes.domain.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<QuoteEntity>)

    @Query("DELETE FROM quotes")
    suspend fun clearAllQuotes()

    @Query("UPDATE quotes SET isFavorite = :isFavorite WHERE id = :_id")
    suspend fun updateFavorite(_id: String, isFavorite: Boolean)

    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quotes WHERE isFavorite = 1")
    fun getAllFavoriteQuotes(): Flow<List<QuoteEntity>>

}