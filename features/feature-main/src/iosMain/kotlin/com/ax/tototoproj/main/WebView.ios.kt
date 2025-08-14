package com.ax.tototoproj.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView

/**
 * iOS平台WebView实现
 */
@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformWebView(url: String) {
    UIKitView(
        factory = {
            WKWebView().apply {
                allowsBackForwardNavigationGestures = true
                loadRequest(NSURLRequest(uRL = NSURL(string = url)))
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}