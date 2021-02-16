package com.dev.itunessearch.domain.usecase

import com.dev.itunessearch.domain.model.ITunesEntity
import com.dev.itunessearch.domain.model.ITunesItem
import com.dev.itunessearch.domain.repository.ITunesRepository
import com.dev.itunessearch.domain.usecase.base.BaseUseCase
import javax.inject.Inject

class GetFavoritesItemsUseCase @Inject constructor(
    private val repository: ITunesRepository
): BaseUseCase<List<ITunesEntity>> {

    override suspend fun buildUseCase(): List<ITunesEntity> {
        return repository.getFavoritesItems()
    }
}