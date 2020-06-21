package org.smirl.julisha.ui.main.models

import android.content.Context
import android.preference.PreferenceManager

class MyPreferences(context: Context?) {
    companion object {

        private const val DARK_STATUS = "io.github.manuelernest.DARK_STATUS"


    }
    private val preferences= PreferenceManager.getDefaultSharedPreferences(context)
    var darkMode=preferences.getInt(DARK_STATUS,2)
        set(value) = preferences.edit().putInt(DARK_STATUS,value).apply()



}