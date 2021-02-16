package com.dev.itunessearch.presentation.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.itunessearch.domain.model.Event
import com.dev.itunessearch.domain.model.ITunesEntity
import com.dev.itunessearch.domain.usecase.GetFavoritesItemsUseCase
import com.dev.itunessearch.domain.usecase.RemoveFromFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesItemsViewModel @Inject constructor(
    private val getFavoritesItemsUseCase: GetFavoritesItemsUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    val data = MutableLiveData<Event<List<ITunesEntity>>>()

    fun loadItems() {
        data.postValue(Event.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data.postValue(Event.success(getFavoritesItemsUseCase.buildUseCase()))
            } catch (exception: Exception) {
                data.postValue(Event.error(exception))
            }
        }
    }

    fun removeItem(id: Long) {
        removeFromFavoritesUseCase.id = id
        viewModelScope.launch(Dispatchers.IO) {
            removeFromFavoritesUseCase.buildUseCase()
        }
    }
}