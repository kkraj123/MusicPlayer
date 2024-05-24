package com.kkrajmp3.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.kkrajmp3.musicplayer.databinding.ActivityMainBinding
import com.kkrajmp3.musicplayer.views.musicListView.pojoClass.MusicList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var receiveMusicObjData : MusicList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (intent != null){
            receiveMusicObjData = intent.getSerializableExtra("musicItem") as MusicList
            Log.d("MainActivity","receiveDataFromMusicObj :" + Gson().toJson(receiveMusicObjData))
        }
        listener()
    }

    private fun listener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}