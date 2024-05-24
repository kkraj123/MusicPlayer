package com.kkrajmp3.musicplayer.views.musicListView.mvvm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MusicVMFactory constructor(private val repository: MusicRepository, val context: Context) :  ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MusicViewModel::class.java)){
            MusicViewModel(this.repository, context) as T
        }else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}