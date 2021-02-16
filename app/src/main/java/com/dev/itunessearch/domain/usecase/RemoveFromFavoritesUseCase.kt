package com.dev.itunessearch.domain.usecase

import com.dev.itunessearch.domain.repository.ITunesRepository
import com.dev.itunessearch.domain.usecase.base.BaseUseCase
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val repository: ITunesRepository
): BaseUseCase<Unit> {

    var id: Long? = null

    override suspend fun buildUseCase() {
        id?.let {
            repository.removeFromFavorites(it)
        }
    }
}