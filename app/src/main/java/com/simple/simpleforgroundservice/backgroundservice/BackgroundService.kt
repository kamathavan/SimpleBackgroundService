package com.simple.simpleforgroundservice.backgroundservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BackgroundService : Service() {

    private val TAG: String = "BackgroundService"

    lateinit var job: Job

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: Method is calling")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: called on background service ")
        job = MainScope().launch {
            var count = 0
            while (count <= 5) {
                delay(1000)
                Log.i(TAG, "onStartCommand: While loop loading $count")
                count++
            }
        }
        stopSelf()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: Method is calling")
        job.cancel()
    }
}