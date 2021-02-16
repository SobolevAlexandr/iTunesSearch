package com.dev.itunessearch.presentation.search

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.itunessearch.domain.model.Event
import com.dev.itunessearch.domain.model.ITunesEntity
import com.dev.itunessearch.domain.model.ITunesItem
import com.dev.itunessearch.domain.model.ITunesItemWrapper
import com.dev.itunessearch.domain.repository.ITunesRepository
import com.dev.itunessearch.domain.usecase.AddToFavoritesUseCase
import com.dev.itunessearch.domain.usecase.GetFavoritesIdsUseCase
import com.dev.itunessearch.domain.usecase.GetITunesItemsUseCase
import com.dev.itunessearch.domain.usecase.RemoveFromFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class SearchItemsViewModel @Inject constructor(
    private val getITunesItemsUseCase: GetITunesItemsUseCase,
    private val getFavoritesIdsUseCase: GetFavoritesIdsUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    val data = MutableLiveData<Event<List<ITunesItemWrapper>>>()

    fun loadItems(filterMode: FilterMode, searchQuery: String?) {
        val query = if (searchQuery.isNullOrEmpty()) DEFAULT_QUERY else searchQuery
        @ITunesRepository.MediaFilter
        val mediaFilter = mapToMediaFilter(filterMode)
        if (getITunesItemsUseCase.searchQuery == query &&
            getITunesItemsUseCase.mediaFilter == mediaFilter) {
            return // early exit, parameters haven't been changed
        }

        getITunesItemsUseCase.mediaFilter = mediaFilter
        getITunesItemsUseCase.searchQuery = query
        data.postValue(Event.loading())

        viewModelScope.launch {
            try {
                val entities = withContext(Dispatchers.IO) {
                    getITunesItemsUseCase.buildUseCase().results
                }
                val favoritesIds = withContext(Dispatchers.IO) {
                    getFavoritesIdsUseCase.buildUseCase().toHashSet()
                }
                val wrappedItems = entities.map {
                    ITunesItemWrapper(it, favoritesIds.contains(it.getId()))
                }
                data.postValue(Event.success(wrappedItems))
            } catch (exception: Exception) {
                data.postValue(Event.error(exception))
            }
        }
    }

    fun addToFavorites(drawable: Drawable?, item: ITunesItem) {
        viewModelScope.launch {
            addToFavoritesUseCase.item = withContext(Dispatchers.Default) {
                val grayscaleBitmap = transformToGrayscaleBitmap(drawable)
                val stream = ByteArrayOutputStream()
                grayscaleBitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                grayscaleBitmap?.recycle()
                mapToITunesEntity(item, byteArray)
            }
            launch(Dispatchers.IO) {
                addToFavoritesUseCase.buildUseCase()
            }
        }
    }

    fun removeFromFavorites(item: ITunesItem) {
        removeFromFavoritesUseCase.id = item.getId()
        viewModelScope.launch(Dispatchers.IO) {
            removeFromFavoritesUseCase.buildUseCase()
        }
    }

    private fun transformToGrayscaleBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        val bitmap = (drawable as BitmapDrawable).bitmap
        val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.RGB_565)
        val canvas = Canvas(newBitmap)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val filter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = filter
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return newBitmap
    }

    private fun mapToITunesEntity(item: ITunesItem, photoByteArray: ByteArray?): ITunesEntity {
        return ITunesEntity(item.getId(), item.artist, item.getName(), photoByteArray)
    }

    @ITunesRepository.MediaFilter
    private fun mapToMediaFilter(filterMode: FilterMode): String {
        return when (filterMode) {
            FilterMode.ALL -> ITunesRepository.MediaFilter.ALL
            FilterMode.MUSIC -> ITunesRepository.MediaFilter.MUSIC
            FilterMode.MOVIE -> ITunesRepository.MediaFilter.MOVIE
            FilterMode.SOFTWARE -> ITunesRepository.MediaFilter.SOFTWARE
        }
    }

    enum class FilterMode {
        ALL,
        MUSIC,
        MOVIE,
        SOFTWARE
    }

    companion object {
        private const val DEFAULT_QUERY = "new"
    }
}