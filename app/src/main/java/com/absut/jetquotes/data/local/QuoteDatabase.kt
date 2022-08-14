package com.absut.jetquotes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.absut.jetquotes.data.local.dao.QuoteDao
import com.absut.jetquotes.data.local.entity.QuoteEntity

@Database(
    entities = [QuoteEntity::class],
    version = 1
)
abstract class QuoteDatabase : RoomDatabase() {

    abstract val getQuoteDao : QuoteDao

}