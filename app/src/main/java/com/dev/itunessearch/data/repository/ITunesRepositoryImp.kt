package com.dev.itunessearch.data.repository

import com.dev.itunessearch.data.source.local.dao.FavoritesItemsDao
import com.dev.itunessearch.data.source.remote.RetrofitService
import com.dev.itunessearch.domain.model.ITunesEntity
import com.dev.itunessearch.domain.model.ITunesItem
import com.dev.itunessearch.domain.model.Response
import com.dev.itunessearch.domain.repository.ITunesRepository

class ITunesRepositoryImp(
    private val favoritesItemsDao: FavoritesItemsDao,
    private val retrofitService: RetrofitService
) : ITunesRepository {

    override suspend fun getItems(
        @ITunesRepository.MediaFilter mediaFilter: String,
        searchQuery: String
    ): Response {
        return retrofitService.search(mediaFilter, searchQuery)
    }

    override suspend fun getFavoritesItems(): List<ITunesEntity> {
        return favoritesItemsDao.getItems()
    }
    override suspend fun getFavoritesIds(): List<Long> {
        return favoritesItemsDao.getIds()
    }

    override suspend fun addToFavorites(item: ITunesEntity) {
        favoritesItemsDao.insert(item)
    }

    override suspend fun removeFromFavorites(id: Long) {
        favoritesItemsDao.deleteById(id)
    }
}