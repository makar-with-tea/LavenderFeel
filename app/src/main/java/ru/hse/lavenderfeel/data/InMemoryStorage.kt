package ru.hse.lavenderfeel.data

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.hse.lavenderfeel.domain.Avatar
import ru.hse.lavenderfeel.domain.DailyEntry
import java.time.LocalDate
import androidx.core.content.edit

object SharedPreferencesStorage {
    private const val PREFS_NAME = "app_storage"
    private const val KEY_AVATAR = "avatar"
    private const val KEY_DAILY_ENTRIES = "daily_entries"
    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveAvatar(avatar: Avatar) {
        val json = gson.toJson(avatar)
        prefs.edit { putString(KEY_AVATAR, json) }
        Log.d("saveAvatar", getAvatar()?.name ?: "no avatar...")
    }

    fun getAvatar(): Avatar? {
        val json = prefs.getString(KEY_AVATAR, null) ?: return null
        Log.d("Avatar", json)
        return gson.fromJson(json, Avatar::class.java)
    }

    fun saveDailyEntry(entry: DailyEntry) {
        val map = getDailyEntriesMap().toMutableMap()
        map[entry.date.toString()] = entry
        saveDailyEntriesMap(map)
    }

    fun getDailyEntry(date: String): DailyEntry? {
        return getDailyEntriesMap()[date]
    }

    fun getAllDailyEntries(): List<DailyEntry> {
        return getDailyEntriesMap().values.sortedByDescending { LocalDate.parse(it.date) }
    }


    private fun getDailyEntriesMap(): Map<String, DailyEntry> {
        val json = prefs.getString(KEY_DAILY_ENTRIES, null) ?: return emptyMap()
        val type = object : TypeToken<Map<String, DailyEntry>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun saveDailyEntriesMap(map: Map<String, DailyEntry>) {
        val json = gson.toJson(map)
        prefs.edit { putString(KEY_DAILY_ENTRIES, json) }
    }

    fun updateAvatarName(name: String) {
        val avatar = getAvatar()
        saveAvatar(avatar?.copy(name = name) ?: Avatar.default())
        Log.d("updateAvatarName", avatar?.name ?: "no avatar...")
    }
}