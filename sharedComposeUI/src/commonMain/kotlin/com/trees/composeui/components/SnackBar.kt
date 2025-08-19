package com.trees.composeui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * @author : lihuiwu
 * @date : 2025/8/19
 * @desc :
 */

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Snackbar 显示位置
 */
enum class SnackPosition {
    TOP, BOTTOM
}

/**
 * Snackbar 配置类
 */
data class SnackConfig(
    val message: String,
    val actionLabel: String? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val position: SnackPosition = SnackPosition.BOTTOM,
    val backgroundColor: Color = Color.Black.copy(alpha = 0.7f),
    val contentColor: Color = Color.White,
    val cornerRadius: Dp = 8.dp
)

/**
 * Snackbar 控制器
 */
// 1. 修改 SnackController
object SnackController {
    // 使用单个共享的 SnackbarHostState
    val snackbarHostState = SnackbarHostState()
    val snackQueue = mutableListOf<SnackConfig>()

    suspend fun showSnackbar(config: SnackConfig) {
        snackQueue.add(config)
        processQueue()
    }

    suspend fun processQueue() {
        if (snackQueue.isEmpty() || snackbarHostState.currentSnackbarData != null) return

        val config = snackQueue.removeFirst()
        snackbarHostState.showSnackbar(
            message = config.message,
            actionLabel = config.actionLabel,
            duration = config.duration
        )
    }
}

// 2. 修改 SnackHost
@Composable
fun SnackHost() {
    // 使用共享的 SnackbarHostState
    val snackbarHostState = remember { SnackController.snackbarHostState }

    // 自动处理队列
    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            SnackController.processQueue()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { data ->
                CustomSnackbar(data, SnackController.snackQueue.firstOrNull())
            }
        )
    }
}


/**
 * 自定义 Snackbar UI
 */
@Composable
fun CustomSnackbar(
    data: SnackbarData,
    config: SnackConfig?
) {
    val backgroundColor = config?.backgroundColor ?: Color.Black.copy(alpha = 0.7f)
    val contentColor = config?.contentColor ?: Color.White
    val cornerRadius = config?.cornerRadius ?: 8.dp

    Snackbar(
        modifier = Modifier.padding(8.dp),
        containerColor = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(cornerRadius),
        action = {
            data.visuals.actionLabel?.let { actionLabel ->
                TextButton(
                    onClick = { data.performAction() }
                ) {
                    Text(
                        text = actionLabel,
                        color = contentColor
                    )
                }
            }
        }
    ) {
        Text(text = data.visuals.message)
    }
}

/**
 * 预览函数
 */
@Composable
fun SnackPreview() {
    var showSnack by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            showSnack = true
            coroutineScope.launch {
                SnackController.showSnackbar(
                    SnackConfig(
                        message = "这是一个Snackbar示例",
                        actionLabel = "操作",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }) {
            Text("显示 Snackbar")
        }
    }

    SnackHost()
}