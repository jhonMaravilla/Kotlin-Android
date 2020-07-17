package com.example.kotlintutorial

import android.content.Context

enum class TempDisplaySetting{
    Fahrenheit, Celsius
}

// We need the context to get reference to get shared preferences
class TempDisplaySettingManager(context: Context) {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    // A function that requires a parameter of any of our enmum class. This updates our sharedpreferences file
    // enums in kotlin has name property which takes its value
    // commit changes it right away while apply does it in the background
    fun updateSetting(setting: TempDisplaySetting){
        preferences.edit().putString("key_temp_display", setting.name).commit()
    }

    // The default value of the setting is Fahrenheit and by doing what we did here takes the String value of the enum instead of its actual value
    fun getTempDisplaySetting(): TempDisplaySetting {
        val settingValue =  preferences.getString("key_temp_display", TempDisplaySetting.Fahrenheit.name) ?: TempDisplaySetting.Fahrenheit.name
        return TempDisplaySetting.valueOf(settingValue)
    }
}