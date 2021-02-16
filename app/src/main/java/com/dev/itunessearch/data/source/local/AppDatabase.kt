package com.dev.itunessearch.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev.itunessearch.data.source.local.dao.FavoritesItemsDao
import com.dev.itunessearch.domain.model.ITunesEntity
import com.dev.itunessearch.domain.model.ITunesItem

@Database(entities = [ITunesEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val favoritesItemsDao: FavoritesItemsDao

    companion object {
        const val DB_NAME = "ITunesItemsDatabase.db"
    }
}
