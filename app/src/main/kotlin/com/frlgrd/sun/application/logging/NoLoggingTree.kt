package com.frlgrd.sun.application.logging

import timber.log.Timber

class NoLoggingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        // Do nothing
    }
}
