package com.dev.itunessearch.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.itunessearch.domain.model.ITunesEntity
import com.dev.itunessearch.domain.model.ITunesItem

@Dao
interface FavoritesItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ITunesEntity)

    @Query("DELETE FROM ITunesEntity WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM ITunesEntity")
    suspend fun getItems(): List<ITunesEntity>

    @Query("SELECT id FROM ITunesEntity")
    suspend fun getIds(): List<Long>
}