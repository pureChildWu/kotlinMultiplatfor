package com.ax.tototoproj.home.news

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ax.tototoproj.list.ListItem
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import com.ax.tototoproj.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

/**
 * 新闻详情屏幕
 */
class NewsDetailScreen(private val newsItem: ListItem) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        var apiResult by remember { mutableStateOf<String?>(null) }
        var isLoading by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        val apiService = remember { ApiService() }


        val navigator = LocalNavigator.currentOrThrow
        var contentVisible by remember { mutableStateOf(false) }
        // 添加弹窗状态控制
        var showCenterDialog by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(100)
            contentVisible = true
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("新闻详情", maxLines = 1) },
                        navigationIcon = {
                            IconButton(onClick = { navigator.pop() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                            }
                        },
                        actions = {
                            // 添加弹窗触发按钮
                            IconButton(onClick = { showCenterDialog = true }) {
                                Icon(Icons.Default.Info, contentDescription = "中间弹窗")
                            }
                            IconButton(onClick = { showBottomSheet = true }) {
                                Icon(Icons.Default.Menu, contentDescription = "底部弹窗")
                            }
                            IconButton(onClick = { /* 收藏 */ }) {
                                Icon(Icons.Default.Favorite, contentDescription = "收藏")
                            }
                            IconButton(onClick = { /* 分享 */ }) {
                                Icon(Icons.Default.Share, contentDescription = "分享")
                            }
                            IconButton(
                                onClick = {
                                    isLoading = true
                                    apiResult = null
                                    coroutineScope.launch(Dispatchers.IO) {
                                        try {
                                            val posts = apiService.getPostsList()
                                            apiResult = "API调用成功！获取到 ${posts.size} 条数据"
                                        } catch (e: Exception) {
                                            apiResult = "API调用失败: ${e.message}"
                                        } finally {
                                            isLoading = false
                                        }
                                    }

                                },
                                enabled = !isLoading
                            ) {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = "测试API",
                                    tint = if (isLoading) Color.Gray else MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    AnimatedVisibility(
                        visible = contentVisible,
                        enter = fadeIn(animationSpec = tween(500)) +
                                slideInVertically(animationSpec = tween(500, delayMillis = 100))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // 新闻标题
                            Text(
                                text = newsItem.title,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // 作者信息
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                // 作者头像
                                Card(
                                    shape = MaterialTheme.shapes.small,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    KamelImage(
                                        resource = asyncPainterResource("https://randomuser.me/api/portraits/men/${newsItem.id % 100}.jpg"),
                                        contentDescription = "作者头像",
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                // 作者名称和时间
                                Column {
                                    Text(
                                        text = "作者 ${newsItem.id}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "2小时前",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }

                            // 新闻图片
                            KamelImage(
                                resource = asyncPainterResource(newsItem.imageUrl),
                                contentDescription = "新闻图片",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .padding(bottom = 16.dp),
                                contentScale = ContentScale.Crop
                            )

                            // 新闻内容
                            Text(
                                text = newsItem.description.repeat(10), // 模拟长文本
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // 相关新闻
                            Text(
                                text = "相关新闻",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            // 相关新闻列表
                            repeat(3) { index ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    onClick = { /* 跳转到相关新闻 */ }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // 缩略图
                                        KamelImage(
                                            resource = asyncPainterResource("https://picsum.photos/100/100?random=${index + 100}"),
                                            contentDescription = "相关新闻",
                                            modifier = Modifier.size(80.dp),
                                            contentScale = ContentScale.Crop
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        // 标题
                                        Text(
                                            text = "相关新闻标题 $index",
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 2
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 底部弹窗
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("底部弹窗内容", style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(16.dp))
                        Text("这里是底部弹窗的示例内容...")
                    }
                }
            }

            // 中间弹窗
            if (showCenterDialog) {
                AlertDialog(
                    onDismissRequest = { showCenterDialog = false },
                    title = { Text("信息提示") },
                    text = { Text("这是中间弹窗的示例内容") },
                    confirmButton = {
                        TextButton(onClick = { showCenterDialog = false }) {
                            Text("确定")
                        }
                    }
                )
            }

            // 结果弹窗
            apiResult?.let { result ->
                AlertDialog(
                    onDismissRequest = { apiResult = null },
                    title = { Text("API测试结果") },
                    text = { Text(result) },
                    confirmButton = {
                        TextButton(onClick = { apiResult = null }) {
                            Text("确定")
                        }
                    }
                )
            }

            // 加载指示器
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("API调用中...", color = Color.White, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }

}