package com.ax.tototoproj.mine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.ax.tototoproj.MR
import com.ax.tototoproj.list.ListItem
import com.ax.tototoproj.mine.profile.ProfileScreen
import com.ax.tototoproj.mine.setting.SettingScreen
import dev.icerock.moko.resources.compose.painterResource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * @author : lihuiwu
 * @date : 2025/8/11
 * @desc : 我的页面
 */
// 我的标签页
object MineTab : Tab {
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 3u,
            title = "我的",
            icon = painterResource(MR.images.setting_icon)
        )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val recommendations = remember { generateRecommendations() }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // 头像
                            KamelImage(
                                resource = asyncPainterResource("https://randomuser.me/api/portraits/men/1.jpg"),
                                contentDescription = "用户头像",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .clickable { navigator.parent?.push(ProfileScreen()) },
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // 姓名和昵称
                            Column {
                                Text(
                                    text = "张三",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "@zhangsan",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            // 设置按钮
                            IconButton(
                                onClick = { navigator.parent?.push(SettingScreen()) }
                            ) {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "设置",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF5F7FA))
            ) {
                // 推荐列表
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(recommendations) { item ->
                        RecommendationItem(item) {
                            // 处理推荐项点击
                        }
                    }
                }
            }
        }
    }
}

// 推荐项组件
@Composable
fun RecommendationItem(item: ListItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 推荐图片
            KamelImage(
                resource = asyncPainterResource(item.imageUrl),
                contentDescription = "推荐图片",
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 推荐内容
            Column {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2
                )
            }
        }
    }
}

// 生成推荐数据
fun generateRecommendations(): List<ListItem> {
    return List(10) { index ->
        ListItem(
            id = index,
            title = "推荐内容 ${index + 1}",
            description = "这是推荐内容的详细描述，包含重要信息和背景内容。",
            imageUrl = "https://picsum.photos/300/200?random=$index",
            likes = (10..100).random()
        )
    }
}
