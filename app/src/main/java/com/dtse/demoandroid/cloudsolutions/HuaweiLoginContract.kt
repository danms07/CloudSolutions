package com.dtse.demoandroid.cloudsolutions

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.result.AuthHuaweiId

class HuaweiLoginContract: ActivityResultContract<HuaweiIdAuthParams, AuthHuaweiId?>() {
    override fun createIntent(context: Context, input: HuaweiIdAuthParams?): Intent {
        val service = HuaweiIdAuthManager.getService(context, input)
        return service.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): AuthHuaweiId? {
        val authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(intent)
        if (authHuaweiIdTask.isSuccessful) {
            return authHuaweiIdTask.result
        }
        return null
    }
}