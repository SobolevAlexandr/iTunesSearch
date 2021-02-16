package com.dev.itunessearch.di.modules

import dagger.Provides
import androidx.room.Room
import android.app.Application
import com.dev.itunessearch.data.source.local.AppDatabase
import com.dev.itunessearch.data.source.local.dao.FavoritesItemsDao
import dagger.Module
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()
    }

    @Provides
    internal fun provideFavoritesItemsDao(appDatabase: AppDatabase): FavoritesItemsDao {
        return appDatabase.favoritesItemsDao
    }
}