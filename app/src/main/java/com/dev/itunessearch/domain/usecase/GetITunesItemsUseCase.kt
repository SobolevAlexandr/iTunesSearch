package com.dev.itunessearch.domain.usecase

import com.dev.itunessearch.domain.model.Response
import com.dev.itunessearch.domain.repository.ITunesRepository
import com.dev.itunessearch.domain.usecase.base.BaseUseCase
import javax.inject.Inject

class GetITunesItemsUseCase @Inject constructor(
    private val repository: ITunesRepository
): BaseUseCase<Response> {

    var searchQuery = ""

    @ITunesRepository.MediaFilter
    var mediaFilter: String = ITunesRepository.MediaFilter.ALL

    override suspend fun buildUseCase(): Response {
        return repository.getItems(mediaFilter, searchQuery)
    }
}