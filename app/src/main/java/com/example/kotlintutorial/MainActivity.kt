package com.example.kotlintutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlintutorial.model.DailyForecast
import com.example.kotlintutorial.repo.ForecastRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

        main_btn_submit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.main_btn_submit -> {
                val zipcode = main_etxt_zipcode.text.toString()

                if (zipcode.length == 0 || zipcode.length < 6) {
                    Toast.makeText(this, R.string.error_zipcode, Toast.LENGTH_LONG).show()
                } else {
                    forecastRepository.loadForecast(zipcode)
                }

                // Recyclerview vertical layout
                main_recyclerview.layoutManager = LinearLayoutManager(this)

                val DailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) {
                    showForecastDetails(it)
                }

                main_recyclerview.adapter = DailyForecastAdapter

                val weeklyForecastObserver = Observer<List<DailyForecast>> { forecastItems ->
                    DailyForecastAdapter.submitList(forecastItems)
                }

                forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)

            }
        }
    }

    private fun showForecastDetails(forecast: DailyForecast) {
        val intent = Intent(this, ForecastDetailsActivity::class.java)
        intent.putExtra("temp", forecast.temp)
        intent.putExtra("desc", forecast.description)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.tempDisplaySetting -> {
                showTempDisplaySetting(this, tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}