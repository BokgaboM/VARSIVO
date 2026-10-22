package com.varsivo.app.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.varsivo.app.tracker.ApplicationEntry

class ApplicationStore(context: Context) {

    private val prefs = context.getSharedPreferences("varsivo_applications", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun all(): List<ApplicationEntry> {
        val json = prefs.getString(KEY_LIST, null) ?: return emptyList()
        val type = object : TypeToken<List<ApplicationEntry>>() {}.type
        return try {
            gson.fromJson<List<ApplicationEntry>>(json, type) ?: emptyList()
        } catch (e: JsonSyntaxException) {
            emptyList()
        }
    }

    fun save(entry: ApplicationEntry) {
        val list = all().toMutableList()
        val index = list.indexOfFirst { it.id == entry.id }
        if (index >= 0) {
            list[index] = entry
        } else {
            list.add(0, entry)
        }
        write(list)
    }

    fun delete(id: String) {
        write(all().filterNot { it.id == id })
    }

    private fun write(list: List<ApplicationEntry>) {
        prefs.edit().putString(KEY_LIST, gson.toJson(list)).apply()
    }

    private companion object {
        const val KEY_LIST = "applications"
    }
}