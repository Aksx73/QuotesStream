package com.absut.jetquotes.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.absut.jetquotes.data.local.QuoteDatabase
import com.absut.jetquotes.data.mapper.toQuote
import com.absut.jetquotes.data.remote.QuoteApi
import com.absut.jetquotes.model.Quote
import com.absut.jetquotes.model.QuoteRemoteKeys
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class QuoteRemoteMediator @Inject constructor(
    private val quoteApi: QuoteApi,
    private val quoteDatabase: QuoteDatabase
) : RemoteMediator<Int, Quote>() {

    private val quoteDao = quoteDatabase.quoteDao
    private val quoteRemoteKeyDao = quoteDatabase.remoteKeyDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Quote>
    ): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            val response = quoteApi.getQuotes(currentPage)
            val endOfPaginationReached = response.totalPages == currentPage

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            quoteDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    quoteDao.clearAllQuotes()
                    quoteRemoteKeyDao.deleteAllRemoteKeys()
                }

                val keys = response.quoteDtos.map { quote ->
                    QuoteRemoteKeys(
                        id = quote.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                quoteRemoteKeyDao.addAllRemoteKeys(keys)
                quoteDao.insertQuotes(response.quoteDtos.map { it.toQuote() })
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }

    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Quote>
    ): QuoteRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                quoteRemoteKeyDao.getRemoteKey(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Quote>
    ): QuoteRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { quote ->
                quoteRemoteKeyDao.getRemoteKey(id = quote.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Quote>
    ): QuoteRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { quote ->
                quoteRemoteKeyDao.getRemoteKey(id = quote.id)
            }
    }

}