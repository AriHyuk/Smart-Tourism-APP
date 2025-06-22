package com.ariawaludin.smarttourismapp.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Helper untuk akses SharedPreferences secara praktis & konsisten.
 */
class SharedPrefHelper(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("smart_tourism_prefs", Context.MODE_PRIVATE)

    // Boolean
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        prefs.getBoolean(key, defaultValue)

    fun setBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    // String
    fun getString(key: String, defaultValue: String? = null): String? =
        prefs.getString(key, defaultValue)

    fun setString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    // Integer
    fun getInt(key: String, defaultValue: Int = 0): Int =
        prefs.getInt(key, defaultValue)

    fun setInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    // Long
    fun getLong(key: String, defaultValue: Long = 0L): Long =
        prefs.getLong(key, defaultValue)

    fun setLong(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    // Float
    fun getFloat(key: String, defaultValue: Float = 0f): Float =
        prefs.getFloat(key, defaultValue)

    fun setFloat(key: String, value: Float) {
        prefs.edit().putFloat(key, value).apply()
    }

    // User (simple)
    fun saveUser(name: String) {
        setString("username", name)
    }

    fun getUser(): String =
        getString("username", "") ?: ""

    // Profile (name + email)
    fun saveProfile(name: String, email: String) {
        prefs.edit()
            .putString("profile_name", name)
            .putString("profile_email", email)
            .apply()
    }

    fun getProfileName(): String =
        prefs.getString("profile_name", "") ?: ""

    fun getProfileEmail(): String =
        prefs.getString("profile_email", "") ?: ""

    // Remove a single value
    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

    // Clear all data
    fun clearAll() {
        prefs.edit().clear().apply()
    }

    // Check if a key exists
    fun contains(key: String): Boolean =
        prefs.contains(key)
}

