package com.example.kotlintutorial

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatTempForDisplay(temp: Float, tempDisplaySetting: TempDisplaySetting): String {
    return when (tempDisplaySetting){
        TempDisplaySetting.Fahrenheit -> String.format("%.2f", temp) + " °F"

        TempDisplaySetting.Celsius -> {
            val celcius = (temp - 32f) * (5f / 9f)
            String.format("%.2f", celcius) + " °C"
        }
    }
}

 fun showTempDisplaySetting(context: Context, tempDisplaySettingManager: TempDisplaySettingManager) {
    val dialogBuilder = AlertDialog.Builder(context)
    dialogBuilder.setTitle("Choose Diplay Units")
    dialogBuilder.setMessage("Choose which temperature unit to use for temperature display")

    dialogBuilder.setPositiveButton("°F") { _, _ ->
        tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
    }
    dialogBuilder.setNeutralButton("°C") { _, _ ->
        tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
    }
    dialogBuilder.setOnDismissListener({
        Toast.makeText(context, "Setting will take effect on app restart", Toast.LENGTH_SHORT)
            .show()
    })


    dialogBuilder.show()
}