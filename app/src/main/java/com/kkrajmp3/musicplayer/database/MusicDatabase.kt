package com.kkrajmp3.musicplayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kkrajmp3.musicplayer.views.musicListView.pojoClass.MusicList

@Database(entities = [MusicList::class], version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase(){
    abstract fun musicDao(): MusicListDao

    companion object{
        @Volatile
        private var INSTANCE: MusicDatabase? = null
        fun getInstance(context: Context): MusicDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MusicDatabase::class.java,
                        "musiclist_databse"
                    ).allowMainThreadQueries().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}