package com.madroid.libs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.madroid.launchersdk.model.AppInfo
import com.madroid.libs.MainActivity
import com.madroid.libs.databinding.AppListChildBinding

class AppsListAdapter(private var apps: List<AppInfo>, private val action: MainActivity) :
    RecyclerView.Adapter<AppsListAdapter.AppsVH>(), Filterable {
    private lateinit var mBinding: AppListChildBinding
    private var appsList: List<AppInfo>

    init {
        appsList = apps
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsVH {
        var inflater: LayoutInflater = LayoutInflater.from(parent.context)
        mBinding = AppListChildBinding.inflate(inflater, parent, false)
        return AppsVH(mBinding)
    }

    override fun onBindViewHolder(holder: AppsVH, position: Int) {
        holder.bind(appsList[position])
    }

    override fun getItemCount(): Int {
        return appsList.size
    }

    /*Filtering results by App Name*/
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val appName = constraint.toString()
                if (appName.isEmpty()) {
                    appsList = apps
                } else {
                    val results = ArrayList<AppInfo>()
                    for (row in appsList) {
                        if (row.name!!.toLowerCase().contains(appName.toLowerCase())) {
                            results.add(row)
                        }
                        appsList = results.toList()
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = appsList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                appsList = results?.values as List<AppInfo>
                notifyDataSetChanged()
            }

        }
    }

    inner class AppsVH(private val binding: AppListChildBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(applicationInfo: AppInfo) {
            binding.appInfo = applicationInfo
            binding.action = action
            binding.executePendingBindings()
        }

    }
}