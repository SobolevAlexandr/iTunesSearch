package com.dev.itunessearch.domain.usecase

import com.dev.itunessearch.domain.model.ITunesEntity
import com.dev.itunessearch.domain.repository.ITunesRepository
import com.dev.itunessearch.domain.usecase.base.BaseUseCase
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val repository: ITunesRepository
): BaseUseCase<Unit> {

    var item: ITunesEntity? = null

    override suspend fun buildUseCase() {
        item?.let {
            repository.addToFavorites(it)
        }
    }
}