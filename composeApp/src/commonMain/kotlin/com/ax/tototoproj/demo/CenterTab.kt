package com.ax.tototoproj.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.ax.tototoproj.MR
import dev.icerock.moko.resources.compose.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * @author : lihuiwu
 * @date : 2025/8/19
 * @desc :
 */
// 新增中间Tab定义
object CenterTab : Tab {
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = "中心",
            icon = painterResource(MR.images.setting_icon)
        )

    @Composable
    override fun Content() {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Text("中心功能页面", style = MaterialTheme.typography.headlineMedium)
//        }
        CenterDemoScreen().Content()
    }
}
