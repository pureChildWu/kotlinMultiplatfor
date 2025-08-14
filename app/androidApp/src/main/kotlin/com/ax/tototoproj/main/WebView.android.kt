package com.ax.tototoproj.main

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Android平台WebView实现
 */
@Composable
actual fun PlatformWebView(url: String) {
    // 添加简单测试日志
    Log.d("DebugTest", "PlatformWebView函数被调用，URL: $url")

    AndroidView(
        factory = { context ->
            // 添加视图创建日志
            Log.d("DebugTest", "WebView实例被创建")

            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.allowFileAccess = true

                // 添加详细配置日志
                Log.d("WebViewConfig", "JavaScript enabled: ${settings.javaScriptEnabled}")
                Log.d("WebViewConfig", "DOM storage enabled: ${settings.domStorageEnabled}")
                Log.d("WebViewConfig", "File access allowed: ${settings.allowFileAccess}")

                webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?,
                        errorCode: Int,
                        description: String?,
                        failingUrl: String?
                    ) {
                        Log.e("WebViewError", "Error $errorCode: $description ($failingUrl)")
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        Log.d("WebViewDebug", "Page loaded: $url")
                    }
                }

                Log.d("WebViewDebug", "Loading URL: $url")
                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}