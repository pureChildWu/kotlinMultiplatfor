package com.ax.tototoproj.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.ax.tototoproj.MR
import com.ax.tototoproj.home.HomeTab
import com.ax.tototoproj.list.ListTab
import com.ax.tototoproj.mine.MineTab
import com.ax.tototoproj.web.WebViewTab
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun MainTabs() {
    TabNavigator(HomeTab) { tabNavigator ->
        Scaffold(
            bottomBar = {
                // 自定义底部导航栏实现
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // 首页标签
                    TabButton(
                        selected = tabNavigator.current == HomeTab,
                        onClick = { tabNavigator.current = HomeTab },
                        icon = if (tabNavigator.current == HomeTab) MR.images.home_icon_selected else MR.images.home_icon,
                        label = "首页"
                    )

                    // 列表标签
                    TabButton(
                        selected = tabNavigator.current == ListTab,
                        onClick = { tabNavigator.current = ListTab },
                        icon = if (tabNavigator.current == ListTab) MR.images.list_icon_selected else MR.images.list_icon,
                        label = "列表"
                    )

                    // 网页标签
                    TabButton(
                        selected = tabNavigator.current == WebViewTab,
                        onClick = { tabNavigator.current = WebViewTab },
                        icon = if (tabNavigator.current == WebViewTab) MR.images.web_icon_selected else MR.images.web_icon,
                        label = "网页"
                    )

                    // 设置标签
                    TabButton(
                        selected = tabNavigator.current == MineTab,
                        onClick = { tabNavigator.current = MineTab },
                        icon = if (tabNavigator.current == MineTab) MR.images.setting_icon_selected else MR.images.setting_icon,
                        label = "我的"
                    )
                }
            }
        ) {
            tabNavigator.current.Content()
        }
    }
}

/**
 * 自定义底部导航栏按钮
 */
@Composable
fun TabButton(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageResource,
    label: String
) {
    val color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

