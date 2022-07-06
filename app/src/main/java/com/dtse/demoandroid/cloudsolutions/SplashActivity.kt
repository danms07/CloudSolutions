package com.dtse.demoandroid.cloudsolutions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.AGConnectAuthCredential
import com.huawei.agconnect.auth.AGConnectUser

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val user = AGConnectAuth.getInstance().currentUser
        if(user!=null){
            syncUser(user)
            jump(Intent(this,MainActivity::class.java))
        }
        else jump(Intent(this,LoginActivity::class.java))
    }

    private fun syncUser(user: AGConnectUser) {
    //Report the user ID to the receiver
        val intent= Intent().apply {
            action=AccountReceiver.ACTION
            putExtra(AccountReceiver.PARAM,AccountReceiver.UID)
            putExtra(AccountReceiver.UID,user.uid)
        }
        sendBroadcast(intent)

    }



    private fun jump(intent: Intent){
        startActivity(intent)
        finish()
    }
}