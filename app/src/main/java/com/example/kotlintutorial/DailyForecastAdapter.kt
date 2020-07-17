package com.example.kotlintutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintutorial.model.DailyForecast

class DailyForecastAdapter(
    private val tempDisplaySettingManager: TempDisplaySettingManager,
    private val clickHandler: ((DailyForecast) -> Unit)
) : ListAdapter<DailyForecast, DailyForecastViewHolder>(DIFF_CONFIG) {
    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<DailyForecast>() {
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_daily_forecast, parent, false)
        return DailyForecastViewHolder(itemView, tempDisplaySettingManager)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickHandler(getItem(position))
        }
    }

}

class DailyForecastViewHolder(
    view: View,
    private val tempDisplaySettingManager: TempDisplaySettingManager
) : RecyclerView.ViewHolder(view) {
    private val temp = view.findViewById<TextView>(R.id.forecast_txt_temp)
    private val desc = view.findViewById<TextView>(R.id.forecast_txt_desc)

    fun bind(dailyForecast: DailyForecast) {
        temp.text = formatTempForDisplay(
            dailyForecast.temp,
            tempDisplaySettingManager.getTempDisplaySetting()
        )
        desc.text = dailyForecast.description
    }
}