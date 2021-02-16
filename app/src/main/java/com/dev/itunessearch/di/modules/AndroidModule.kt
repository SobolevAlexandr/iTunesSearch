package com.dev.itunessearch.di.modules

import android.app.Application
import android.content.Context
import com.dev.itunessearch.presentation.ITunesApplication
import dagger.Module
import dagger.Provides

@Module
class AndroidModule {

    @Provides
    internal fun provideApplication(): Application = ITunesApplication.application

    @Provides
    internal fun provideContext(): Context = ITunesApplication.application
}