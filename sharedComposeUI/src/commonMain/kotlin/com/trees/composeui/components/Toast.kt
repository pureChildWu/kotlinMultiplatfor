package com.trees.composeui.components

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * @author : lihuiwu
 * @date : 2025/8/19
 * @desc :
 */
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Toast 显示位置
 */
enum class ToastPosition {
    TOP, CENTER, BOTTOM
}

/**
 * Toast 配置类
 */
// 修改 ToastConfig 类
data class ToastConfig @OptIn(ExperimentalTime::class) constructor(
    val id: Long = Clock.System.now().toEpochMilliseconds(), // 使用 Kotlin 多平台时间 API
    val message: String,
    val duration: Long = 2000L,
    val position: ToastPosition = ToastPosition.BOTTOM,
    val backgroundColor: Color = Color.Black.copy(alpha = 0.7f),
    val textColor: Color = Color.White,
    val cornerRadius: Dp = 8.dp,
    val shape: Shape = RoundedCornerShape(cornerRadius), // 圆角形状
    val padding: Dp = 16.dp
)

/**
 * Toast 控制器
 */
object ToastController {
    private var _currentConfig by mutableStateOf<ToastConfig?>(null)
    val currentConfig: ToastConfig?
        @Composable get() = _currentConfig

    fun show(
        message: String,
        duration: Long = 2000L,
        position: ToastPosition = ToastPosition.CENTER
    ) {
        _currentConfig = ToastConfig(
            message = message,
            duration = duration,
            position = position
        )
    }

    fun hide() {
        _currentConfig = null
    }
}

/**
 * Toast 组件
 */
@Composable
fun ToastHost() {
    val config = ToastController.currentConfig

    AnimatedVisibility(
        visible = config != null,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        config?.let { safeConfig ->
            LaunchedEffect(safeConfig.id) {
                delay(safeConfig.duration)
                ToastController.hide()
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(safeConfig.padding),
                contentAlignment = when (safeConfig.position) {
                    ToastPosition.TOP -> Alignment.TopCenter
                    ToastPosition.CENTER -> Alignment.Center
                    ToastPosition.BOTTOM -> Alignment.BottomCenter
                }
            ) {
                Surface(
                    color = safeConfig.backgroundColor,
                    shape = safeConfig.shape, // 使用圆角形状
                ) {
                    Text(
                        text = safeConfig.message,
                        color = safeConfig.textColor,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

/**
 * 预览函数
 */
@OptIn(ExperimentalTime::class)
@Composable
fun ToastPreview() {
//    var showToast by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically),
    ) {
        Button(onClick = {
//            showToast = true
            ToastController.show("Toast #${Clock.System.now().toEpochMilliseconds()}")
        }) {
            Text("显示 Toast")
        }
    }

    ToastHost()
}
