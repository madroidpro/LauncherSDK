package com.madroid.libs

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.madroid.launchersdk.LauncherUtil
import com.madroid.launchersdk.model.AppInfo
import com.madroid.libs.adapter.AppsListAdapter
import com.madroid.libs.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding;
    lateinit var mAdapter:AppsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val launcherUtil = LauncherUtil
        initLauncher(launcherUtil)
        initSearchListener()

    }

    private fun initSearchListener() {
        mBinding.appSearch.setOnQueryTextListener(object :SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mAdapter.filter.filter(newText)
                return true
            }

        })
    }

    private fun initLauncher(launcherUtil: LauncherUtil) {
        launcherUtil.initReceiver(this);
        val info: List<AppInfo>? = launcherUtil.getApplicationList(this)
        mAdapter = AppsListAdapter(info!!, this);
        mBinding.adapter = mAdapter
        mBinding.executePendingBindings()
    }

    /* Launch application */
    fun openApp(info: AppInfo){
        val launchIntent: Intent? = info.packageName?.let {
            this.packageManager.getLaunchIntentForPackage(
                it
            )
        }
        startActivity(launchIntent)

    }
}