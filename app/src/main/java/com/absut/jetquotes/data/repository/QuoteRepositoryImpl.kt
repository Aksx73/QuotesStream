package com.absut.jetquotes.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.absut.jetquotes.data.local.QuoteDatabase
import com.absut.jetquotes.data.paging.QuoteRemoteMediator
import com.absut.jetquotes.data.remote.QuoteApi
import com.absut.jetquotes.domain.repository.QuoteRepository
import com.absut.jetquotes.model.Quote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class QuoteRepositoryImpl @Inject constructor(
    private val quoteApi: QuoteApi,
    private val quoteDatabase: QuoteDatabase
) : QuoteRepository {

    override fun getQuotes(): Flow<PagingData<Quote>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100
            ),
            remoteMediator = QuoteRemoteMediator(quoteApi, quoteDatabase),
            pagingSourceFactory = { quoteDatabase.quoteDao.getAllQuotes() }
        ).flow
    }

    override suspend fun addRemoveFromFavorite(id: String, isFavorite: Boolean) {
       quoteDatabase.quoteDao.updateFavorite(id, isFavorite)
    }

    override fun getFavoriteQuotes(): Flow<List<Quote>> {
        return quoteDatabase.quoteDao.getAllFavoriteQuotes()
    }

}