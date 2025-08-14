package com.ax.tototoproj.mine.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ax.tototoproj.list.ListItem
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay

/**
 * @author : lihuiwu
 * @date : 2025/8/11
 * @desc : 个人中心页面
 */
class ProfileScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var contentVisible by remember { mutableStateOf(false) }
        val userPosts = remember { generateUserPosts() }

        // 延迟显示内容以展示动画
        LaunchedEffect(Unit) {
            delay(100)
            contentVisible = true
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("个人中心") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "返回")
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
                AnimatedVisibility(
                    visible = contentVisible,
                    enter = fadeIn(animationSpec = tween(500))
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        // 个人信息区域
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color(0xFF6A11CB),
                                                Color(0xFF2575FC)
                                            )
                                        ),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                // 头像
                                KamelImage(
                                    resource = asyncPainterResource("https://randomuser.me/api/portraits/men/1.jpg"),
                                    contentDescription = "用户头像",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .align(Alignment.TopCenter)
                                        .clip(CircleShape)
                                        .border(3.dp, Color.White, CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                                // 姓名和昵称
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(bottom = 16.dp)
                                ) {
                                    Text(
                                        text = "张三",
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "@zhangsan",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                }

                                // 编辑资料按钮
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(bottom = 16.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.White.copy(alpha = 0.2f))
                                        .clickable { /* 编辑资料 */ }
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "编辑资料",
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                            }

                            // 统计信息
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                StatItem("关注", "128")
                                StatItem("粉丝", "1.2K")
                                StatItem("获赞", "5.8K")
                            }
                        }

                        // 功能区
                        item {
                            Text(
                                text = "我的功能",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                            )

                            // 功能网格
                            val functions = listOf(
                                FunctionItem("我的收藏", Icons.Default.Bookmark),
                                FunctionItem("我的订单", Icons.Default.ShoppingCart),
                                FunctionItem("浏览历史", Icons.Default.History),
                                FunctionItem("消息中心", Icons.Default.Email),
                                FunctionItem("账号设置", Icons.Default.Settings),
                                FunctionItem("关于我们", Icons.Default.Person)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                functions.chunked(3).forEach { rowItems ->
                                    Column {
                                        rowItems.forEach { item ->
                                            FunctionCard(item)
                                        }
                                    }
                                }
                            }
                        }

                        // 个人动态
                        item {
                            Text(
                                text = "我的动态",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                            )
                        }

                        // 动态列表
                        items(userPosts) { post ->
                            PostCard(post)
                        }
                    }
                }
            }
        }
    }
}

// 统计项组件
@Composable
fun StatItem(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

// 功能项组件
@Composable
fun FunctionCard(item: FunctionItem) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(8.dp)
            .clickable { /* 处理点击 */ },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// 动态卡片组件
@Composable
fun PostCard(post: ListItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // 动态内容
            Text(
                text = post.description,
                style = MaterialTheme.typography.bodyLarge
            )

            // 动态图片
            if (post.imageUrl.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                KamelImage(
                    resource = asyncPainterResource(post.imageUrl),
                    contentDescription = "动态图片",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f/9f)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // 互动信息
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "2小时前",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Row {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "点赞",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${post.likes}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

// 功能项数据类
data class FunctionItem(val title: String, val icon: ImageVector)

// 生成用户动态数据
fun generateUserPosts(): List<ListItem> {
    return List(5) { index ->
        ListItem(
            id = index,
            title = "动态 ${index + 1}",
            description = "这是我发布的第${index + 1}条动态，分享生活中的美好瞬间。",
            imageUrl = if (index % 2 == 0) "https://picsum.photos/600/400?random=$index" else "",
            likes = (10..200).random()
        )
    }
}