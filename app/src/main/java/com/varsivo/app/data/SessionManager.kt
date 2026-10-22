

package com.varsivo.app.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("varsivo_session", Context.MODE_PRIVATE)

    fun saveSession(token: String, name: String, role: String) {
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putString(KEY_NAME, name)
            .putString(KEY_ROLE, role)
            .apply()
    }

    fun token(): String? = prefs.getString(KEY_TOKEN, null)
    fun userName(): String? = prefs.getString(KEY_NAME, null)
    fun role(): String? = prefs.getString(KEY_ROLE, null)
    fun isLoggedIn(): Boolean = !token().isNullOrEmpty()

    fun clear() = prefs.edit().clear().apply()

    private companion object {
        const val KEY_TOKEN = "token"
        const val KEY_NAME = "name"
        const val KEY_ROLE = "role"
    }
}
