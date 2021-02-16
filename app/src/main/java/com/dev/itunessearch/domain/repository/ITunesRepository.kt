package com.dev.itunessearch.domain.repository

import androidx.annotation.StringDef
import com.dev.itunessearch.domain.model.ITunesEntity
import com.dev.itunessearch.domain.model.Response

interface ITunesRepository {

    suspend fun getItems(@MediaFilter mediaFilter: String, searchQuery: String): Response

    suspend fun getFavoritesItems(): List<ITunesEntity>

    suspend fun getFavoritesIds(): List<Long>

    suspend fun addToFavorites(item: ITunesEntity)

    suspend fun removeFromFavorites(id: Long)

    @Target(
        AnnotationTarget.CLASS,
        AnnotationTarget.PROPERTY,
        AnnotationTarget.LOCAL_VARIABLE,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.FUNCTION
    )
    @StringDef(
        MediaFilter.ALL,
        MediaFilter.MUSIC,
        MediaFilter.MOVIE,
        MediaFilter.SOFTWARE
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class MediaFilter {
        companion object {
            const val ALL = "all"
            const val MUSIC = "music"
            const val MOVIE = "movie"
            const val SOFTWARE = "software"
        }
    }
}