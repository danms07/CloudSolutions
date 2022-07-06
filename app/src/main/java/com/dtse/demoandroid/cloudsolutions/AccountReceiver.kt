package com.dtse.demoandroid.cloudsolutions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

class AccountReceiver: BroadcastReceiver() {

    companion object {
        private const val TAG="Receiver"
        const val PARAM="param"
        const val UID="uid"
        const val TOKEN="pushToken"
        const val ACTION="ACTION_REGISTER_USER"
    }

    private var uid:String?=null
    private var token:String?=null
    private var context:Context?=null

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.extras?.let {
            val key=it.getString(PARAM)
            val value=it.getString(key)
            checkParamValues(key!!,value!!)
        }
    }

    fun register(context:Context){
        Log.d(TAG,"Registering Receiver")
        context.registerReceiver(this, IntentFilter(ACTION))
        this.context=context
    }

    private fun unregister(){
        Log.d(TAG,"UnregisteringReceiver")
        context?.unregisterReceiver(this)
    }

    private fun checkParamValues(key: String, value: String) {
        Log.e(TAG,"Received $key")
        when(key){
            UID -> {
                uid=value
                token?.let{registerInServer(value,it)}
            }
            TOKEN -> {
                token=value
                uid?.let { registerInServer(it,value) }
            }
        }
    }

    private fun registerInServer(uid: String, token:String) {
        //invoke cloud functions to insert the user and token
        //Loggin for testing
        Log.d("Usuario","UID $uid\tToken $token")
        //Release the receiver
        unregister()
    }
}