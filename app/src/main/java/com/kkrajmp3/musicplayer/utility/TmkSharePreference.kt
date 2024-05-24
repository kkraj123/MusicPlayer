package com.kkrajmp3.musicplayer.utility

import android.content.Context
import android.content.SharedPreferences
import android.text.BoringLayout
import java.util.prefs.Preferences

class TmkSharePreference private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    private val ONBOARDING_CALL = "ONBOARDING_CALL"
    companion object{
        private const val SHARED_NAME = "tmkPreference"
        @Volatile
        private var instance: TmkSharePreference? = null

        fun getInstance(context: Context) : TmkSharePreference {
            return instance ?: synchronized(this){
                instance ?: TmkSharePreference(context).also { instance = it }
            }
        }
    }

    fun setFirstCallOnboarding(isFirstCall: Boolean){
        sharedPreferences.edit().putBoolean(ONBOARDING_CALL, isFirstCall).apply()
    }
    fun getFirstCallOnboarding() :Boolean{
        return sharedPreferences.getBoolean(ONBOARDING_CALL, false)
    }

}