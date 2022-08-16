package com.absut.jetquotes.di

import com.absut.jetquotes.data.local.QuoteDatabase
import com.absut.jetquotes.data.remote.QuoteApi
import com.absut.jetquotes.data.repository.QuoteRepositoryImpl
import com.absut.jetquotes.domain.repository.QuoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesQuoteRepository(
        quoteApi: QuoteApi,
        quoteDatabase: QuoteDatabase
    ): QuoteRepository {
        return QuoteRepositoryImpl(quoteApi, quoteDatabase)
    }
}