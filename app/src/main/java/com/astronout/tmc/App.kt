package com.astronout.tmc

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.chibatching.kotpref.Kotpref

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}