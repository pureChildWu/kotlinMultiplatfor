package com.ax.tototoproj.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.trees.composeui.components.LoadingPreview

/**
 * @author : lihuiwu
 * @date : 2025/8/19
 * @desc :
 */
class CenterDemoScreen : Screen{
    @Composable
    override fun Content() {
        var navigator = LocalNavigator.currentOrThrow
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(0.dp, 30.dp, 0.dp, 0.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                item {
                    Box(
                        modifier = Modifier
                            .clickable {
                                navigator.parent?.push(LoadingDemoScreen())
                            }
                            .background(Color(0xFF000000), shape = RoundedCornerShape(10.dp))
                            .size(200.dp, 40.dp),
                        contentAlignment = Alignment.Center // 添加这行确保内容居中
                    ) {
                        Text(
                            "加载Loading",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center // 水平居中
                            ),
                            modifier = Modifier.wrapContentHeight(Alignment.CenterVertically) // 垂直居中
                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .clickable {
                                navigator.parent?.push(PullToRefreshScreen())
                            }
                            .background(Color(0xFF000000), shape = RoundedCornerShape(10.dp))
                            .size(200.dp, 40.dp),
                        contentAlignment = Alignment.Center // 添加这行确保内容居中
                    ) {
                        Text(
                            "下拉刷新",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center // 水平居中
                            ),
                            modifier = Modifier.wrapContentHeight(Alignment.CenterVertically) // 垂直居中
                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .clickable {
                                navigator.parent?.push(DialogScreen())
                            }
                            .background(Color(0xFF000000), shape = RoundedCornerShape(10.dp))
                            .size(200.dp, 40.dp),
                        contentAlignment = Alignment.Center // 添加这行确保内容居中
                    ) {
                        Text(
                            "Dialog",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center // 水平居中
                            ),
                            modifier = Modifier.wrapContentHeight(Alignment.CenterVertically) // 垂直居中
                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .clickable {
                                navigator.parent?.push(ToastScreen())
                            }
                            .background(Color(0xFF000000), shape = RoundedCornerShape(10.dp))
                            .size(200.dp, 40.dp),
                        contentAlignment = Alignment.Center // 添加这行确保内容居中
                    ) {
                        Text(
                            "Toast",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center // 水平居中
                            ),
                            modifier = Modifier.wrapContentHeight(Alignment.CenterVertically) // 垂直居中
                        )
                    }
                }
            }

        }
    }
}