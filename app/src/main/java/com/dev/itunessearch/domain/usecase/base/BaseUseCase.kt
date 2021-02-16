package com.dev.itunessearch.domain.usecase.base

interface BaseUseCase<T> {

    suspend fun buildUseCase(): T
}