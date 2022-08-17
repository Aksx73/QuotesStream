package com.absut.jetquotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.absut.jetquotes.domain.repository.QuoteRepository
import com.absut.jetquotes.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    val quotes = repository.getQuotes().cachedIn(viewModelScope)

    fun updateFavoriteStatus(id: String, value: Boolean) = viewModelScope.launch {
        repository.addRemoveFromFavorite(id, value)
    }

    fun favoriteQuotes() = liveData {
       repository.getFavoriteQuotes().collect {
            emit(it)
        }
    }

    /*private val _favoriteQuotes: MutableStateFlow<State<List<Quote>>> = MutableStateFlow(State.loading())
   val favoriteQuotes: StateFlow<State<List<Quote>>> = _favoriteQuotes

   fun getFavorite() = viewModelScope.launch {
       repository.getFavoriteQuotes()
           .map { response ->
               State.fromResource(response)
           }
           .collect{ state ->
               _favoriteQuotes.value = state
           }
   }*/


}