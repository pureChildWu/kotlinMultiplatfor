package com.trees.composeui.components

/**
 * @author : lihuiwu
 * @date : 2025/8/19
 * @desc :
 */

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun CommonCard(
    modifier: Modifier = Modifier, // 默认的修饰符，用于自定义布局属性
    contentPadding: PaddingValues = PaddingValues(16.dp), // 内容填充，默认为16dp
    elevation: Int = 2, // 阴影高度，默认值为2
    content: @Composable () -> Unit // 可组合的内容块
) {
    Card(
        modifier = modifier, // 应用外部传入的修饰符
        shape = RoundedCornerShape(16.dp), // 设置卡片形状为圆角矩形，圆角半径为16dp
        elevation = CardDefaults.cardElevation(elevation.dp), // 设置卡片阴影的高度
        colors = CardDefaults.cardColors(containerColor = Color.White) // 设置卡片背景颜色为白色
    ) {
        Box(modifier = Modifier.padding(contentPadding)) { // 添加内容填充，并在Box中放置内容
            content() // 调用并显示可组合的内容块
        }
    }
}