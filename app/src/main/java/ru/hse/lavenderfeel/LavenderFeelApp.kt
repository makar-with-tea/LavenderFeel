package ru.hse.lavenderfeel

import android.app.Application
import ru.hse.lavenderfeel.data.SharedPreferencesStorage

class LavenderFeelApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesStorage.init(this)
    }
}