package com.absut.jetquotes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.absut.jetquotes.data.local.dao.QuoteDao
import com.absut.jetquotes.data.local.dao.QuoteRemoteKeyDao
import com.absut.jetquotes.model.Quote
import com.absut.jetquotes.model.QuoteRemoteKeys

@Database(
    entities = [Quote::class, QuoteRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class QuoteDatabase : RoomDatabase() {

    abstract val quoteDao : QuoteDao
    abstract val remoteKeyDao:QuoteRemoteKeyDao

}