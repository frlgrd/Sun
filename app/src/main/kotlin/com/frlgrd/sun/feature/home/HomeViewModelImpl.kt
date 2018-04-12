package com.frlgrd.sun.feature.home

import android.app.Application
import com.frlgrd.sun.common.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class HomeViewModelImpl @Inject constructor(app: Application) : BaseViewModel(app), HomeViewModel {
    override fun doStuff() {
        Timber.d("stuff.")
    }
}