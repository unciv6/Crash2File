package com.peak.crashsdk

import android.app.Application

/**
 * create by peak at 2021/7/12
 */

private const val TAG = "CrashEngine"

class CrashEngine(private var application: Application) {
    private var installed = false

    init {
        installed = true

        installCrashHandler()
    }


    private fun installCrashHandler() {

        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            logThrowable(e, application)
        }

    }


    fun hasInstalled():  Boolean {
        return installed
    }


}