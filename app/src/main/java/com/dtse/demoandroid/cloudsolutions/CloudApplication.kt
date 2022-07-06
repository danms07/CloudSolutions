package com.dtse.demoandroid.cloudsolutions

import android.app.Application

class CloudApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AccountReceiver().register(this)
    }
}