package com.frlgrd.sun.application

import android.app.Application
import com.frlgrd.sun.BuildConfig
import com.frlgrd.sun.application.logging.NoLoggingTree
import timber.log.Timber
import toothpick.Toothpick
import toothpick.smoothie.module.SmoothieApplicationModule

class SunApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Logging
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else NoLoggingTree())

        val appScope = Toothpick.openScope(this)
        appScope.installModules(SmoothieApplicationModule(this), SunApplicationModule(this))

    }
}