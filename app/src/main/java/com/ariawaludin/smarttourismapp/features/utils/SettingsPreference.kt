package com.ariawaludin.smarttourismapp.features.utils


import android.content.Context

class iSettingsPreference(context: Context) {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    var isDarkMode: Boolean
        get() = prefs.getBoolean("dark_mode", false)
        set(value) = prefs.edit().putBoolean("dark_mode", value).apply()

    var notificationsEnabled: Boolean
        get() = prefs.getBoolean("notifications_enabled", true)
        set(value) = prefs.edit().putBoolean("notifications_enabled", value).apply()

    var locationEnabled: Boolean
        get() = prefs.getBoolean("location_enabled", true)
        set(value) = prefs.edit().putBoolean("location_enabled", value).apply()

    fun clear() {
        prefs.edit().clear().apply()
    }
}
