package com.dev.itunessearch.presentation.search

import android.graphics.drawable.Drawable
import com.dev.itunessearch.domain.model.ITunesItem

interface OnFavoritesClickListener {

    fun addToFavorites(drawable: Drawable?, item: ITunesItem)
    fun removeFromFavorites(item: ITunesItem)
}