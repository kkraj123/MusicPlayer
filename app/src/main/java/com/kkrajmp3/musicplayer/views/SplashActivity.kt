package com.kkrajmp3.musicplayer.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.databinding.DataBindingUtil
import com.kkrajmp3.musicplayer.MainActivity
import com.kkrajmp3.musicplayer.R
import com.kkrajmp3.musicplayer.databinding.ActivitySplashBinding
import com.kkrajmp3.musicplayer.utility.TmkSharePreference
import com.kkrajmp3.musicplayer.views.musicListView.MusicListActivity
import com.kkrajmp3.musicplayer.views.walkthrough.OnboardingActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var tmkSharePreference: TmkSharePreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        tmkSharePreference = TmkSharePreference.getInstance(this)

        Handler(Looper.getMainLooper()).postDelayed({
            if (tmkSharePreference.getFirstCallOnboarding()){
                val mainIntent = Intent(this, MusicListActivity::class.java)
                startActivity(mainIntent)
                finish()
            }else{
                val mainIntent = Intent(this, OnboardingActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }, 2000)
    }
}