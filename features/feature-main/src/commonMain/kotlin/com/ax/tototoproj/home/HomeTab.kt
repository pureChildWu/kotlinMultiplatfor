package com.ax.tototoproj.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.ax.tototoproj.MR
import com.ax.tototoproj.list.ListItem // 使用统一的 ListItem 类型
import com.ax.tototoproj.home.news.NewsDetailScreen
import dev.icerock.moko.resources.compose.painterResource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * @author : lihuiwu
 * @date : 2025/8/11
 * @desc :
 */

// 首页标签页
object HomeTab : Tab {
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "首页",
            icon = painterResource(MR.images.home_icon)
        )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        // 获取当前导航器
        val navigator = LocalNavigator.currentOrThrow
        val newsList = remember { generateMockData() }
        val listState = rememberLazyListState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("今日头条") },
                    actions = {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "收藏",
                            tint = MaterialTheme.colorScheme.primary
                        )
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
                LazyColumn(contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 50.dp), state = listState) {
                    items(newsList) { item ->
                        NewsItem(
                            item = item,
                            onClick = {
                                // 修复导航：使用正确方式打开新闻详情
                                // 原错误代码：navigator.push(NewsDetailScreen(item))
                                // 新方案：使用普通导航而非标签页导航
                                navigator.parent?.push(NewsDetailScreen(item))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NewsItem(item: ListItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // 添加垂直间距
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 新闻图片
            KamelImage(
                resource = asyncPainterResource(item.imageUrl),
                contentDescription = "新闻图片",
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 新闻内容
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp), // 添加垂直内边距
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "作者 ${item.id}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "2小时前",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

// 生成模拟数据
fun generateMockData(): List<ListItem> {
    return List(20) { index ->
        ListItem(
            id = index,
            title = "今日头条新闻 #${index + 1}：重要事件发生",
            description = "这是新闻的详细描述，包含重要信息和背景内容。",
            imageUrl = "https://picsum.photos/300/200?random=$index",
            likes = (10..500).random()
        )
    }
}