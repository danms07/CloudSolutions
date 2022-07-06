package com.dtse.demoandroid.cloudsolutions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.AGConnectUser
import com.huawei.agconnect.auth.HwIdAuthProvider
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton

class LoginActivity : AppCompatActivity() {

    private val hwidLauncher=registerForActivityResult(HuaweiLoginContract()){ result ->handleHuaweiID(result)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<HuaweiIdAuthButton>(R.id.hwidBtn).setOnClickListener { huaweiIdLogin() }
        findViewById<Button>(R.id.anonBtn).setOnClickListener { anonymousLogin() }

    }

    private fun anonymousLogin() {
        AGConnectAuth.getInstance().signInAnonymously().addOnSuccessListener {
            // onSuccess
            syncUserAndJump(it.user)
        }.addOnFailureListener {
            // onFail
        }
    }

    private fun huaweiIdLogin(){
        val authParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setAuthorizationCode()
            .setAccessToken()
            .createParams()
        hwidLauncher.launch(authParams)

    }


    private fun handleHuaweiID(authHuaweiId: AuthHuaweiId?) {
        authHuaweiId?.let {
            val credential = HwIdAuthProvider.credentialWithToken(it.accessToken)
            AGConnectAuth.getInstance().signIn(credential).addOnSuccessListener{ result ->
                syncUserAndJump(result.user)
            }
        }
    }

    private fun syncUserAndJump(user: AGConnectUser) {
        //Report the user ID to the receiver
        val intent= Intent().apply {
            action=AccountReceiver.ACTION
            putExtra(AccountReceiver.PARAM,AccountReceiver.UID)
            putExtra(AccountReceiver.UID,user.uid)
        }
        sendBroadcast(intent)
        startActivity(Intent(this,MainActivity::class.java))
    }
}