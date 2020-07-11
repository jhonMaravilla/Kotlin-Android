package com.example.kotlintutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlintutorial.model.DailyForecast
import com.example.kotlintutorial.repo.ForecastRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        main_btn_submit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
             R.id.main_btn_submit -> {
                 val zipcode = main_etxt_zipcode.text.toString()

                 if(zipcode.length == 0 || zipcode.length > 6){
                     Toast.makeText(this, R.string.error_zipcode, Toast.LENGTH_LONG).show()
                 } else {
                     forecastRepository.loadForecast(zipcode)
                 }

                 // Recyclerview vertical layout
                 main_recyclerview.layoutManager = LinearLayoutManager(this)

                 val DailyForecastAdapter = DailyForecastAdapter() {
                     val msg = getString(R.string.forecast_clicked_format, it.temp, it.description)

                     Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                 }
                 main_recyclerview.adapter = DailyForecastAdapter

                 val weeklyForecastObserver = Observer<List<DailyForecast>> {forecastItems ->
                    DailyForecastAdapter.submitList(forecastItems)
                 }

                 forecastRepository.weeklyForecast.observe(this,weeklyForecastObserver)

            }
        }
    }

}