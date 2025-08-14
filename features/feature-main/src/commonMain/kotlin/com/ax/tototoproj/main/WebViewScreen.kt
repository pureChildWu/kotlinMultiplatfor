package com.ax.tototoproj.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey

/**
 * 跨平台WebView屏幕
 * @param url 要加载的网页URL
 */
class WebViewScreen(private val url: String) : Screen {
    override val key = uniqueScreenKey
    
    @Composable
    override fun Content() {
        Text(
            "欢迎回来",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 40.dp)
        )
        PlatformWebView(url)
    }
}

/**
 * 公共WebView接口
 */
@Composable
expect fun PlatformWebView(url: String)