package com.frlgrd.sun.common

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.smoothie.module.SmoothieActivityModule
import toothpick.smoothie.module.SmoothieSupportActivityModule

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    protected abstract fun layoutRes(): Int

    /**
     *
     */
    protected abstract fun injector(): Module

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        injectDependencies()
        startViewModelObservations()
    }

    @CallSuper
    override fun onDestroy() {
        Toothpick.closeScope(this)
        super.onDestroy()
    }

    private fun injectDependencies() {
        val activityScope = Toothpick.openScopes(application, this)
        activityScope.installModules(SmoothieSupportActivityModule(this), SmoothieActivityModule(this), injector())
        Toothpick.inject(this, activityScope)
    }

    open fun startViewModelObservations() {}

}