package com.dtse.demoandroid.cloudsolutions

import android.content.Intent
import android.util.Log
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage

class MyHmsMessageService: HmsMessageService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
    }
    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        token?.let {
            Log.d("onNewToken",it)
            publishToken(it)
        }
    }

    private fun publishToken(token:String) {
        val intent= Intent().apply {
            action = AccountReceiver.ACTION
            putExtra(AccountReceiver.PARAM,AccountReceiver.TOKEN)
            putExtra(AccountReceiver.TOKEN,token)
        }
        sendBroadcast(intent)
    }
}