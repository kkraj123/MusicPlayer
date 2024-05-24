package com.kkrajmp3.musicplayer.views.walkthrough.fragments

import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.kkrajmp3.musicplayer.R
import com.kkrajmp3.musicplayer.databinding.FragmentOnboarding1Binding


class OnboardingFragment1 : Fragment() {
   private lateinit var binding: FragmentOnboarding1Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_onboarding1, container, false)

        return binding.root
    }

}