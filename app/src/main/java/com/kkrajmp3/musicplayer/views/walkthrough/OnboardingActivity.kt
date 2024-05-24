package com.kkrajmp3.musicplayer.views.walkthrough

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.kkrajmp3.musicplayer.R
import com.kkrajmp3.musicplayer.databinding.ActivityOnboardingBinding
import com.kkrajmp3.musicplayer.views.musicListView.MusicListActivity
import com.kkrajmp3.musicplayer.views.walkthrough.fragments.OnboardingFragment1
import com.kkrajmp3.musicplayer.views.walkthrough.fragments.OnboardingFragment2
import com.kkrajmp3.musicplayer.views.walkthrough.fragments.OnboardingFragment3

class OnboardingActivity : AppCompatActivity() {
    lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)

        val adapter = OnboardingAdapter(this)
        adapter.addFragment(OnboardingFragment1())
        adapter.addFragment(OnboardingFragment2())
        adapter.addFragment(OnboardingFragment3())
        binding.onboardingViewPager.adapter = adapter
        binding.onboardingViewPager.currentItem = 0

        binding.nextBtn.setOnClickListener(View.OnClickListener {
           if (binding.onboardingViewPager.currentItem == 2){
               navigateToHomeScreen();
           }else{
               binding.onboardingViewPager.currentItem += 1
           }
        })
    }

    private fun navigateToHomeScreen() {
        startActivity(Intent(this, MusicListActivity::class.java))
    }

}