package com.trees.composeui.components

/**
 * @author : lihuiwu
 * @date : 2025/8/19
 * @desc :
 */
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * 通用标题栏组件，包含返回按钮和标题
 * @param title 标题文本
 * @param onBackClick 返回按钮点击回调
 * @param modifier 修饰符
 * @param backgroundColor 背景色，默认为 MaterialTheme.colorScheme.primary
 * @param titleColor 标题颜色，默认为 MaterialTheme.colorScheme.onPrimary
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBack(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    titleColor: Color = MaterialTheme.colorScheme.onPrimary,
    // 新增：自定义返回按钮
    backIcon: @Composable (() -> Unit)? = null,
    // 新增：控制标题是否真正居中在屏幕
    centerTitleOnScreen: Boolean = true
) {
    CenterAlignedTopAppBar(
        title = {
            if (centerTitleOnScreen) {
                Text(
                    text = title,
                    color = titleColor,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                // 默认的 AppBar 内居中
                Text(
                    text = title,
                    color = titleColor,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            // 自定义返回按钮或默认按钮
            if (backIcon != null) {
                backIcon()
            } else {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "返回",
                        tint = titleColor
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = titleColor,
            navigationIconContentColor = titleColor
        ),
        modifier = modifier
    )
}
