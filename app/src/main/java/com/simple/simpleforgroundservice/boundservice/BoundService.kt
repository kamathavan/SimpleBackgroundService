package com.simple.simpleforgroundservice.boundservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BoundService : Service() {

    private val myBoundServiceBinder = BoundServiceBinder()

    override fun onBind(intent: Intent): IBinder {
        return myBoundServiceBinder
    }

    fun getProgressStatus(): Flow<Float> {
        var progress = 0f
        return flow {
            while (progress <= 1.0f) {
                progress += .1f
                delay(1000)
                emit(progress)
            }
        }
    }

    inner class BoundServiceBinder : Binder() {
        fun getMyBoundService() = this@BoundService
    }

}