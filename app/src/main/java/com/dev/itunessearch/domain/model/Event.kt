package com.dev.itunessearch.domain.model

data class Event<out T>(val status: Status, val data: T?, val error: Throwable?) {

    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null, null)
        }

        fun <T> success(data: T): Event<T> {
            return Event(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable? = null): Event<T> {
            return Event(Status.ERROR, null, error)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}