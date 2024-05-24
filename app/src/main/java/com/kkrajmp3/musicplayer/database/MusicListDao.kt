package com.kkrajmp3.musicplayer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kkrajmp3.musicplayer.views.musicListView.pojoClass.MusicList

@Dao
interface MusicListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusicList(musicList : List<MusicList>)

    @Query("SELECT * FROM music_list")
    fun getAllMusicFromDatabase() : List<MusicList>

    @Delete
    fun deleteItem(musicList: MusicList) :Int
}