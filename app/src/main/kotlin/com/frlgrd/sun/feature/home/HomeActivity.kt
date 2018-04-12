package com.frlgrd.sun.feature.home

import com.frlgrd.sun.R
import com.frlgrd.sun.common.BaseActivity
import javax.inject.Inject

class HomeActivity : BaseActivity(R.layout.activity_home) {

    override fun module() = HomeModule(this)

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onInject() {
        super.onInject()
        viewModel.doStuff()
    }
}