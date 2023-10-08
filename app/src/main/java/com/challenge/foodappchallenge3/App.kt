package com.challenge.foodappchallenge3

import android.app.Application
import com.challenge.foodappchallenge3.data.local.database.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}