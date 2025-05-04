package com.ariawaludin.smarttourismapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("smart_tourism_prefs", Context.MODE_PRIVATE)

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun saveUser(name: String) {
        prefs.edit().putString("username", name).apply()
    }

    fun getUser(): String? {
        return prefs.getString("username", null)
    }

    fun clearUser() {
        prefs.edit().clear().apply()
    }
}
