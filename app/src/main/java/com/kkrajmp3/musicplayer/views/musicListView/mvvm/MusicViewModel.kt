package com.kkrajmp3.musicplayer.views.musicListView.mvvm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkrajmp3.musicplayer.database.MusicDatabase
import com.kkrajmp3.musicplayer.utility.ResponseWrapper
import com.kkrajmp3.musicplayer.views.musicListView.pojoClass.MusicList
import kotlinx.coroutines.launch

class MusicViewModel(val repository: MusicRepository, context: Context)  :ViewModel(){
    var allAudioSongsList =ArrayList<MusicList>()
    val musicDao = MusicDatabase.getInstance(context).musicDao()

    fun getAllSongs(context: Context){
        viewModelScope.launch {
            try {
                allAudioSongsList.addAll(repository.getAllAudio(context))
                musicDao.insertMusicList(repository.getAllAudio(context))
            }catch (ex: Exception){
                ex.printStackTrace()
            }
        }
    }
    fun getAllMusicFromDatabase(){
        allAudioSongsList.addAll(musicDao.getAllMusicFromDatabase())
    }
}