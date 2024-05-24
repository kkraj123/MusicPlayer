package com.kkrajmp3.musicplayer.views.musicListView.pojoClass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "music_list")
data class MusicList(
    @PrimaryKey
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val duration: Long,
    val path: String,
): Serializable
