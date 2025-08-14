package com.ax.tototoproj.web

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.ax.tototoproj.MR
import com.ax.tototoproj.main.WebViewScreen
import dev.icerock.moko.resources.compose.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * @author : lihuiwu
 * @date : 2025/8/11
 * @desc :
 */
// WebView标签页
object WebViewTab : Tab {
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = "网页",
            icon = painterResource(MR.images.web_icon)
        )

    @Composable
    override fun Content() {
        WebViewScreen("https://www.baidu.com").Content()
    }
}