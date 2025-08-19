package com.ax.tototoproj.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.ax.tototoproj.MR
import com.ax.tototoproj.demo.CenterTab
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

                    // 新增中间突出Tab
                    CenterTabButton(
                        selected = tabNavigator.current == CenterTab,
                        onClick = { tabNavigator.current = CenterTab },
                        icon = if (tabNavigator.current == CenterTab) MR.images.setting_icon else MR.images.setting_icon_selected
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

// 新增中间突出Tab组件
@Composable
fun CenterTabButton(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageResource
) {
    val color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    val elevation by animateDpAsState(if (selected) 8.dp else 0.dp, animationSpec = tween(300))
    val scale by animateFloatAsState(if (selected) 1.2f else 1f, animationSpec = tween(300))

    Box(
        modifier = Modifier
            .size(60.dp)
            .offset(y = (-15).dp) // 向上突出
            .shadow(elevation = elevation, shape = CircleShape)
            .background(MaterialTheme.colorScheme.surface, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "中心",
            tint = color,
            modifier = Modifier
                .size(32.dp)
                .scale(scale)
        )
    }
}


