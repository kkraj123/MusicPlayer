package com.kkrajmp3.musicplayer.views.musicListView.mvvm

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore
import com.kkrajmp3.musicplayer.utility.ResponseWrapper
import com.kkrajmp3.musicplayer.views.musicListView.pojoClass.MusicList
import java.io.File

class MusicRepository {
    @SuppressLint("Range", "SuspiciousIndentation")
    fun getAllAudio(context: Context) : ArrayList<MusicList>{
        val tempList = ArrayList<MusicList>()
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA)
        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null,
            MediaStore.Audio.Media.DATE_ADDED + " DESC", null)
        if (cursor != null){
            if (cursor.moveToFirst())
                do {
                    val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albumC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val musicList = MusicList(id=idC,title= titleC, album = albumC, artist= artistC, duration = durationC, path = pathC)
                    val file = File(musicList.path)
                    if (file.exists())
                        tempList.add(musicList)
                }while (cursor.moveToNext())
            cursor.close()
        }
        return tempList
    }
}