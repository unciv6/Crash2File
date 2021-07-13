package com.peak.crashsdk

import android.app.Application

/**
 * create by peak at 2021/7/12
 */
class CrashSdk {

    companion object {

        var engine: CrashEngine? = null


        @Synchronized
        fun init(application: Application) {
            if (engine == null) {
                engine = CrashEngine(application)
            }
        }


        fun isInitialized(): Boolean {
            engine?.let {
                return engine!!.hasInstalled()
            }
            return false
        }


    }

}