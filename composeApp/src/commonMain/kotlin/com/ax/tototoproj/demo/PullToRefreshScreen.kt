package com.ax.tototoproj.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.trees.composeui.components.TitleBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PullToRefreshScreen : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = remember { CoroutineScope(Dispatchers.Main) }

        // 模拟数据
        var items by remember { mutableStateOf((1..20).map { "项目 $it" }) }
        var refreshing by remember { mutableStateOf(false) }
        var loadingMore by remember { mutableStateOf(false) } // 上拉加载状态
        var canLoadMore by remember { mutableStateOf(true) } // 是否还有更多数据可加载

        // 创建下拉刷新状态
        val refreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = {
                refreshing = true
                coroutineScope.launch {
                    delay(1500)
                    items = (1..20).shuffled().map { "刷新项目 $it" }
                    refreshing = false
                    canLoadMore = true // 重置加载更多状态
                }
            },
//            refreshThreshold = 100.dp
        )

        // 创建列表状态用于检测滚动位置
        val listState = rememberLazyListState()

        // 监听滚动位置，触发上拉加载
        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo }
                .collect { visibleItems ->
                    if (visibleItems.isNotEmpty() && canLoadMore) {
                        val lastItem = visibleItems.last()
                        // 当最后一个项目可见时加载更多
                        if (lastItem.index == items.size - 1 && !loadingMore) {
                            loadingMore = true
                            coroutineScope.launch {
                                delay(1000) // 模拟网络请求
                                // 添加新数据
                                val newItems = (items.size + 1..items.size + 10).map { "新项目 $it" }
                                items = items + newItems
                                loadingMore = false

                                // 模拟数据加载完毕
                                if (items.size > 50) {
                                    canLoadMore = false
                                }
                            }
                        }
                    }
                }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            TitleBack(
                title = "下拉刷新 & 上拉加载",
                onBackClick = { navigator.pop() }
            )

            // 下拉刷新区域
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(refreshState)
            ) {
                // 内容列表
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    state = listState // 添加列表状态
                ) {
                    items(items) { item ->
                        Text(
                            text = item,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    // 上拉加载指示器
                    if (loadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    // 没有更多数据提示
                    if (!canLoadMore) {
                        item {
                            Text(
                                text = "没有更多数据了",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                // 下拉刷新指示器
                PullRefreshIndicator(
                    refreshing = refreshing,
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                        .size(40.dp),
//                        .clip(RoundedCornerShape(10.dp)),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}