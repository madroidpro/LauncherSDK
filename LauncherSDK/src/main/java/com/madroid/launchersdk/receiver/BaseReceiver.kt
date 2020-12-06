package com.madroid.launchersdk.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.madroid.launchersdk.R

/*For notifying App installed/uninstalled*/
class BaseReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context!!, R.string.app_installed, Toast.LENGTH_LONG).show()
    }
}