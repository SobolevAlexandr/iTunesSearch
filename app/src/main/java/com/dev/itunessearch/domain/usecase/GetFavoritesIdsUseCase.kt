package com.dev.itunessearch.domain.usecase

import com.dev.itunessearch.domain.repository.ITunesRepository
import com.dev.itunessearch.domain.usecase.base.BaseUseCase
import javax.inject.Inject

class GetFavoritesIdsUseCase @Inject constructor(
    private val repository: ITunesRepository
): BaseUseCase<List<Long>> {

    override suspend fun buildUseCase(): List<Long> {
        return repository.getFavoritesIds()
    }
}