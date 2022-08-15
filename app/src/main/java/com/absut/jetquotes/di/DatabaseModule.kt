package com.absut.jetquotes.di

import android.app.Application
import androidx.room.Room
import com.absut.jetquotes.data.local.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(app: Application): QuoteDatabase {
        return Room.databaseBuilder(
            app,
            QuoteDatabase::class.java,
            "quote_db"
        ).build()
    }

}