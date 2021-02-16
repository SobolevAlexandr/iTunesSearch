package com.dev.itunessearch.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Response(val results: List<ITunesItem>)

class ITunesItem(
    private val trackId: Long?,
    private val collectionId: Long?,
    private val trackName: String?,
    private val collectionName: String?,
    @SerializedName("artistName")
    val artist: String?,
    @SerializedName("artworkUrl100")
    val photoUrl: String
) {
    fun getId(): Long {
        return collectionId ?: trackId ?: 0L
    }

    fun getName(): String {
        return collectionName ?: trackName ?: ""
    }
}

data class ITunesItemWrapper(
    val item: ITunesItem,
    var isFavorite: Boolean
)

@Suppress("ArrayInDataClass")
@Entity(tableName = "ITunesEntity")
data class ITunesEntity(
    @PrimaryKey
    val id: Long,
    val artist: String?,
    val name: String?,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val photoGrayscale: ByteArray?
)