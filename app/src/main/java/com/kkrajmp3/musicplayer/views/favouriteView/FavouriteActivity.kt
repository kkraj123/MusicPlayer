package com.kkrajmp3.musicplayer.views.favouriteView

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kkrajmp3.musicplayer.R
import com.kkrajmp3.musicplayer.databinding.ActivityFabouriteBinding

class FavouriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFabouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fabourite)
        setContentView(binding.root)


    }
}