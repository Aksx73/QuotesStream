package com.absut.jetquotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.absut.jetquotes.domain.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    var isDark: Boolean = true
    val quotes = repository.getQuotes().cachedIn(viewModelScope)

    fun updateFavoriteStatus(id: String, value: Boolean) = viewModelScope.launch {
        repository.addRemoveFromFavorite(id, value)
    }

    fun favoriteQuotes() = liveData {
       repository.getFavoriteQuotes().collect {
            emit(it)
        }
    }

}