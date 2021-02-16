package com.dev.itunessearch.presentation

import android.app.Application
import com.dev.itunessearch.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class ITunesApplication : DaggerApplication() {

    private val applicationInjector = DaggerAppComponent.builder()
        .application(this)
        .build()

    init {
        application = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return applicationInjector
    }

    companion object {
        lateinit var application: Application
            private set
    }
}