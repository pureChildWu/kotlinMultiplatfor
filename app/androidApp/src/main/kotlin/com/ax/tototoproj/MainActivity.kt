package com.ax.tototoproj

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // 设置沉浸式状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            App()
        }
        Log.d("DebugTest", "MainActivity created") // 添加这行

    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}