package com.ax.tototoproj.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.ax.tototoproj.MR
import dev.icerock.moko.resources.compose.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * 列表标签页
 */
object ListTab : Tab {
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = "列表",
            icon = painterResource(MR.images.list_icon)
        )

    @Composable
    override fun Content() {
        // 使用新创建的分页列表屏幕
        PagedListScreen().Content()
    }
}