package com.frlgrd.sun.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.smoothie.module.SmoothieActivityModule
import toothpick.smoothie.module.SmoothieSupportActivityModule

abstract class BaseActivity(@LayoutRes private val layoutRes: Int) : AppCompatActivity() {

    protected abstract fun module(): Module

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        onInject()
    }

    @CallSuper
    override fun onDestroy() {
        Toothpick.closeScope(this)
        super.onDestroy()
    }

    @CallSuper
    open fun onInject() {
        val activityScope = Toothpick.openScopes(application, this)
        activityScope.installModules(SmoothieSupportActivityModule(this), SmoothieActivityModule(this), module())
        Toothpick.inject(this, activityScope)
    }

    fun <T : ViewModel> getViewModel(viewModelClass: Class<T>): T =
            ViewModelProviders.of(this, Toothpick.openScopes(application)
                    .getInstance(ViewModelFactory::class.java))
                    .get(viewModelClass)
}