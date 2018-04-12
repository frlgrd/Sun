package com.frlgrd.sun.feature.home

import toothpick.config.Module

class HomeModule(activity: HomeActivity) : Module() {
    init {
        bind(HomeViewModel::class.java).toInstance(activity.getViewModel(HomeViewModelImpl::class.java))
    }
}