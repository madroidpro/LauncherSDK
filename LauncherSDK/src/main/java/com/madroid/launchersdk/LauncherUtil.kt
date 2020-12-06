package com.madroid.launchersdk

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import com.madroid.launchersdk.model.AppInfo
import com.madroid.launchersdk.receiver.BaseReceiver
import java.util.*

object LauncherUtil {

    /*Initiate intent filter to listen for package add/ remove broadcast*/
    fun initReceiver(context: Context?) {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_INSTALL)
        intentFilter.addDataScheme("package")
        context!!.registerReceiver(BaseReceiver(), intentFilter)
    }

    /*Get list of Apps Installed*/
    fun getApplicationList(context: Context?): List<AppInfo>? {
        val pm = context!!.packageManager
        val appsList: MutableList<AppInfo> = ArrayList()
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        val allApps = pm.queryIntentActivities(i, 0)
        for (ri in allApps) {
            val app = AppInfo()
            app.packageName = ri.activityInfo.packageName
            app.icon = ri.activityInfo.loadIcon(pm)
            app.name = ri.loadLabel(pm).toString()
            app.versionName = pm.getPackageInfo(ri.activityInfo.packageName, 0).versionName
            app.versionCode = pm.getPackageInfo(ri.activityInfo.packageName, 0).versionCode
            app.activityName =
                pm.getLaunchIntentForPackage(ri.activityInfo.packageName)?.component?.className
            appsList.add(app)
        }
        return appsList.sortedBy { it.name }
    }
}