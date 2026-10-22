package com.varsivo.app.data

import android.content.Context

class ApsStore(context: Context) {

    private val prefs = context.getSharedPreferences("varsivo_aps", Context.MODE_PRIVATE)

    fun save(aps: Int) {
        prefs.edit().putInt(KEY_APS, aps).apply()
    }

    fun apsOrNull(): Int? {
        val value = prefs.getInt(KEY_APS, -1)
        return if (value < 0) null else value
    }

    private companion object {
        const val KEY_APS = "aps"
    }
}