package com.kkrajmp3.musicplayer.views.recentViews

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.kkrajmp3.musicplayer.R
import com.kkrajmp3.musicplayer.databinding.ActivityRecentBinding

class RecentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recent)
        setContentView(binding.root)

    }
}