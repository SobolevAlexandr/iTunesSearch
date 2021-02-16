package com.dev.itunessearch.di

import com.dev.itunessearch.di.modules.*
import com.dev.itunessearch.presentation.ITunesApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AndroidModule::class,
    FragmentModule::class,
    DatabaseModule::class,
    NetworkModule::class,
    SharedPrefsModule::class,
    ViewModelModule::class
])
interface AppComponent : AndroidInjector<ITunesApplication> {
    override fun inject(app: ITunesApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: ITunesApplication): Builder
        fun build(): AppComponent
    }
}