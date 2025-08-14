package com.ax.tototoproj.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay

/**
 * 分页列表屏幕
 */
class PagedListScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.statusBarsPadding(),
                topBar = {
                    // 透明顶部栏
                    TopAppBar(
                        title = { Text("精选内容") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent
                        )
                    )
                }
            ) { paddingValues ->
                PagedListView(Modifier.padding(paddingValues))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagedListView(modifier: Modifier = Modifier) {
    // 模拟分页数据
    val items = remember { generateMockData() }
    val listState = rememberLazyListState()
    var isLoading by remember { mutableStateOf(false) }
    var visibleItems by remember { mutableStateOf(10) }

    // 加载更多逻辑（简化版）
    LaunchedEffect(Unit) {
        while (true) {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            if (lastVisibleItem != null && lastVisibleItem.index >= visibleItems - 1 && !isLoading) {
                isLoading = true
                delay(1000) // 模拟网络请求
                visibleItems += 10
                isLoading = false
            }
            delay(100) // 检查间隔
        }
    }

    // 更柔和的渐变背景
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF5F7FA),  // 浅灰蓝
            Color(0xFFE4E7EB)   // 更浅的灰
        ),
        startY = 0f,
        endY = 1000f
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentPadding = PaddingValues(bottom = 60.dp)
        ) {
            itemsIndexed(items.take(visibleItems)) { index, item ->
                // 将Spacer移出AnimatedVisibility
                Column(Modifier.fillMaxWidth()) {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(500, delayMillis = index * 50)) +
                            scaleIn(animationSpec = tween(300, delayMillis = index * 50))
                    ) {
                        ListItemCard(item = item, index = index)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            // 加载指示器
            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListItemCard(item: ListItem, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = { /* 点击处理 */ }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 图片背景（使用Kamel）
            KamelImage(
                resource = asyncPainterResource(item.imageUrl),
                contentDescription = "图片 $index",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            // 内容卡片
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                            startY = 0.4f
                        )
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "喜欢",
                        tint = Color(0xFFFF6B6B),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${item.likes} 喜欢",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// 模拟数据模型
data class ListItem(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val likes: Int
)

// 生成模拟数据
fun generateMockData(): List<ListItem> {
    return List(50) { index ->
        ListItem(
            id = index,
            title = "精选内容 #${index + 1}",
            description = "这是精选内容的描述，展示了分页列表的实现效果。",
            imageUrl = "https://picsum.photos/600/400?random=$index",
            likes = (10..500).random()
        )
    }
}