package com.simple.simpleforgroundservice

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.simple.simpleforgroundservice.foregroundservice.ForegroundService
import com.simple.simpleforgroundservice.ui.theme.SimpleForgroundServiceTheme

class BackGroundServiceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleForgroundServiceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BackGroundServiceButton(
                        startService = {
                            Intent(this, ForegroundService::class.java).also {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    startForegroundService(it)
                                } else {
                                    startService(it)
                                }
                            }
                        },
                        stopService = {
                            Intent(this, ForegroundService::class.java).also {
                                stopService(it)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BackGroundServiceButton(startService: () -> Unit, stopService: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            startService.invoke()
        }) {
            Text(text = "Start Service")
        }
        Button(onClick = {
            stopService.invoke()
        }) {
            Text(text = "Stop Service")
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    SimpleForgroundServiceTheme {
        Greeting2("Android")
    }
}