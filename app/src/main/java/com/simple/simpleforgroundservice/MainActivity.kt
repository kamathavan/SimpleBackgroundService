package com.simple.simpleforgroundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simple.simpleforgroundservice.boundservice.BoundService
import com.simple.simpleforgroundservice.ui.theme.SimpleForgroundServiceTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var myBoundService: BoundService
    private var mBound = false

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(component: ComponentName?, binder: IBinder) {
            val service = binder as BoundService.BoundServiceBinder
            myBoundService = service.getMyBoundService()
            mBound = true
        }

        override fun onServiceDisconnected(component: ComponentName?) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleForgroundServiceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /* DownloadFile(
                         isBound = mBound,
                         myBoundService = myBoundService
                     )*/
                    var progress by remember {
                        mutableStateOf(0f)
                    }

                    val coroutineScope = rememberCoroutineScope()

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                if (mBound) {
                                    coroutineScope.launch {
                                        myBoundService.getProgressStatus().collect {
                                            progress = it
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.align(
                                alignment = Alignment.TopCenter
                            )
                        ) {
                            Text(text = "Download File")
                        }

                        LinearProgressIndicator(
                            progress = progress,
                            modifier = Modifier
                                .align(
                                    alignment = Alignment.Center
                                )
                                .height(20.dp),
                            color = Color.Green,
                        )
                    }
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        Intent(this, BoundService::class.java).also {
            bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }

}

@Composable
fun DownloadFile(
    isBound: Boolean,
    myBoundService: BoundService
) {
    var progress by remember {
        mutableStateOf(0f)
    }

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                if (isBound) {
                    coroutineScope.launch {
                        myBoundService.getProgressStatus().collect {
                            progress = it
                        }
                    }
                }
            },
            modifier = Modifier.align(
                alignment = Alignment.TopCenter
            )
        ) {
            Text(text = "Download File")
        }

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .align(
                    alignment = Alignment.Center
                )
                .height(10.dp),
            color = Color.Red,
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleForgroundServiceTheme {
        Greeting("Android")
    }
}