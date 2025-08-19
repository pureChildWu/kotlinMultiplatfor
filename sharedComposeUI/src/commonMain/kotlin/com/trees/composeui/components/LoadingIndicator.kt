package com.trees.composeui.components

/**
 * @author : lihuiwu
 * @date : 2025/8/19
 * @desc :
 */
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color
    )
}

@Composable
fun PulseDotLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    dotCount: Int = 3,
    dotSize: Dp = 8.dp,
    animationDuration: Int = 600
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scales = (0 until dotCount).map { index ->
        val delay = index * (animationDuration / dotCount)
        infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration
                    0.5f at delay
                    1.2f at delay + 200
                    0.5f at delay + 400
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        scales.forEach { scale ->
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .graphicsLayer { scaleX = scale.value; scaleY = scale.value }
                    .background(color, CircleShape)
            )
        }
    }
}

@Composable
fun RotatingRingLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 32.dp,
    strokeWidth: Dp = 4.dp,
    animationDuration: Int = 1000
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = color,
                startAngle = rotation,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx()),
                size = Size(size.toPx(), size.toPx())
            )
        }
    }
}

@Composable
fun WaveLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    barCount: Int = 5,
    barWidth: Dp = 4.dp,
    barHeight: Dp = 24.dp,
    animationDuration: Int = 800
) {
    val infiniteTransition = rememberInfiniteTransition()
    val heights = (0 until barCount).map { index ->
        val delay = index * (animationDuration / barCount)
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration
                    0.3f at delay
                    1f at delay + 200
                    0.3f at delay + 400
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        heights.forEach { height ->
            Box(
                modifier = Modifier
                    .width(barWidth)
                    .height(barHeight * height.value)
                    .background(color, RoundedCornerShape(2.dp))
            )
        }
    }
}

// 菊花加载动画 (iOS风格)
@Composable
fun SpinnerLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 32.dp,
    strokeWidth: Dp = 3.dp,
    animationDuration: Int = 1000
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = color.copy(alpha = 0.2f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx()),
                size = Size(size.toPx(), size.toPx())
            )

            drawArc(
                color = color,
                startAngle = rotation - 90,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round),
                size = Size(size.toPx(), size.toPx())
            )
        }
    }
}

// 弹跳圆点加载动画
@Composable
fun BouncingDotsLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    dotSize: Dp = 12.dp,
    animationDuration: Int = 600
) {
    val infiniteTransition = rememberInfiniteTransition()
    val offsets = (0 until 3).map { index ->
        val delay = index * (animationDuration / 3)
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (-8f),
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration
                    0f at delay
                    (-8f) at delay + 200
                    0f at delay + 400
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        offsets.forEach { offset ->
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .offset(y = offset.value.dp)
                    .background(color, CircleShape)
            )
        }
    }
}

// 旋转方块加载动画
@Composable
fun RotatingSquaresLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 24.dp,
    animationDuration: Int = 1200
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotations = (0 until 2).map { index ->
        val delay = index * (animationDuration / 2)
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 180f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration
                    0f at delay
                    180f at delay + 600
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(
        modifier = modifier.size(size * 1.5f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .graphicsLayer {
                    rotationZ = rotations[0].value
                    rotationY = rotations[1].value
                }
                .background(color.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
        )

        Box(
            modifier = Modifier
                .size(size * 0.7f)
                .graphicsLayer {
                    rotationZ = rotations[1].value
                    rotationX = rotations[0].value
                }
                .background(color, RoundedCornerShape(2.dp))
        )
    }
}

// 进度条加载动画
@Composable
fun ProgressBarLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    height: Dp = 8.dp,
    animationDuration: Int = 1000
) {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .background(color, RoundedCornerShape(4.dp))
        )
    }
}

// 旋转圆点追逐动画
@Composable
fun ChasingDotsLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 40.dp,
    animationDuration: Int = 1000
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size / 2)
                .graphicsLayer {
                    rotationZ = rotation
                }
        ) {
            Box(
                modifier = Modifier
                    .size(size / 4)
                    .align(Alignment.TopStart)
                    .background(color, CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(size / 4)
                    .align(Alignment.BottomEnd)
                    .background(color, CircleShape)
            )
        }
    }
}

// 折叠方块动画
@Composable
fun FoldingCubeLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 40.dp,
    animationDuration: Int = 1200
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotations = (0 until 4).map { index ->
        val delay = index * (animationDuration / 4)
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 90f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration
                    0f at delay
                    90f at delay + 300
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // 四个方块分别位于四个象限
        Box(
            modifier = Modifier
                .size(size / 2)
                .align(Alignment.TopStart)
                .graphicsLayer {
                    rotationZ = rotations[0].value
                }
                .background(color.copy(alpha = 0.7f))
        )
        Box(
            modifier = Modifier
                .size(size / 2)
                .align(Alignment.TopEnd)
                .graphicsLayer {
                    rotationZ = rotations[1].value
                }
                .background(color.copy(alpha = 0.7f))
        )
        Box(
            modifier = Modifier
                .size(size / 2)
                .align(Alignment.BottomStart)
                .graphicsLayer {
                    rotationZ = rotations[2].value
                }
                .background(color.copy(alpha = 0.7f))
        )
        Box(
            modifier = Modifier
                .size(size / 2)
                .align(Alignment.BottomEnd)
                .graphicsLayer {
                    rotationZ = rotations[3].value
                }
                .background(color.copy(alpha = 0.7f))
        )
    }
}


@Composable
fun ChrysanthemumLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 64.dp,
    petalCount: Int = 12,
    animationDuration: Int = 2000
) {
    val infiniteTransition = rememberInfiniteTransition()

    // 花瓣旋转角度动画
    val rotations = (0 until petalCount).map { index ->
        val delay = index * (animationDuration / petalCount)
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration
                    0f at delay
                    360f at delay + animationDuration
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    // 花瓣缩放动画
    val scales = (0 until petalCount).map { index ->
        val delay = index * (animationDuration / petalCount)
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration
                    0.3f at delay
                    1f at delay + (animationDuration / 3)
                    0.3f at delay + (animationDuration * 2 / 3)
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // 花蕊中心点
        Box(
            modifier = Modifier
                .size(size * 0.2f)
                .background(color, CircleShape)
        )

        // 花瓣
        repeat(petalCount) { index ->
            val angle = 360f * index / petalCount
            val distance = size * 0.35f

            Box(
                modifier = Modifier
                    .offset(
                        // 使用 distance.value 获取 Float 值进行计算
                        x = (distance.value * cos(angle * PI / 180)).dp,
                        y = (distance.value * sin(angle * PI / 180)).dp
                    )
                    .graphicsLayer {
                        rotationZ = rotations[index].value
                        scaleX = scales[index].value
                        scaleY = scales[index].value
                    }
                    .size(size * 0.3f, size * 0.1f)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}

/**
 * iOS 风格的活动指示器（菊花加载动画）
 *
 * @param size 指示器大小（直径）
 * @param strokeWidth 花瓣宽度
 * @param color 主颜色
 * @param backgroundColor 背景颜色（可选）
 * @param text 底部显示的文本（可选）
 * @param textColor 文本颜色
 */
@Composable
fun IosLoadingIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    strokeWidth: Dp = 3.dp,
    color: Color = Color(0xFF007AFF), // iOS 系统蓝
    backgroundColor: Color? = null,
    text: String? = null,
    textColor: Color = Color.Black.copy(alpha = 0.6f)
) {
    // 动画状态：0f 到 1f 的无限循环
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    )

    // 计算花瓣数量（iOS 标准为 12 个）
    val petalCount = 12

    // 计算每个花瓣的透明度变化
    val petalAlphas = remember(petalCount) {
        List(petalCount) { index ->
            // 花瓣透明度从 1.0 递减到 0.0
            (petalCount - index).toFloat() / petalCount
        }
    }

    Box(
        modifier = modifier
            .then(
                if (backgroundColor != null) {
                    Modifier.background(
                        color = backgroundColor,
                        shape = MaterialTheme.shapes.medium
                    )
                } else {
                    Modifier
                }
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 菊花指示器
            Canvas(
                modifier = Modifier
                    .size(size)
            ) {
                // 计算花瓣角度间隔
                val anglePerPetal = 360f / petalCount

                // 计算花瓣长度和位置
                val center = Offset(size.toPx() / 2, size.toPx() / 2)
                val radius = size.toPx() / 2 - strokeWidth.toPx() / 2

                // 绘制所有花瓣
                for (i in 0 until petalCount) {
                    // 计算当前花瓣的角度（考虑旋转动画）
                    val angle = i * anglePerPetal + rotation * 360f

                    // 计算花瓣透明度（带延迟效果）
                    val alphaIndex = (i + (rotation * petalCount).toInt()) % petalCount
                    val alpha = petalAlphas[alphaIndex]

                    // 绘制单个花瓣
                    drawPetal(
                        center = center,
                        radius = radius,
                        angle = angle,
                        alpha = alpha,
                        color = color,
                        strokeWidth = strokeWidth.toPx()
                    )
                }
            }

            // 底部文本
            if (!text.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = text,
                    color = textColor,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

/**
 * 绘制单个花瓣
 */
private fun DrawScope.drawPetal(
    center: Offset,
    radius: Float,
    angle: Float,
    alpha: Float,
    color: Color,
    strokeWidth: Float
) {
    // 将角度转换为弧度
    val angleRad = angle * (PI.toFloat() / 180f)

    // 计算花瓣起点和终点
    val startPoint = Offset(
        x = center.x + (radius * 0.7f) * cos(angleRad),
        y = center.y + (radius * 0.7f) * sin(angleRad)
    )

    val endPoint = Offset(
        x = center.x + radius * cos(angleRad),
        y = center.y + radius * sin(angleRad)
    )

    // 绘制花瓣（带透明度和颜色）
    drawLine(
        color = color.copy(alpha = alpha),
        start = startPoint,
        end = endPoint,
        strokeWidth = strokeWidth,
        cap = androidx.compose.ui.graphics.StrokeCap.Round
    )
}

@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        IosLoadingIndicator(
            size = 60.dp,
            strokeWidth = 4.dp,
            color = Color.White,
            text = "加载数据中...",
            textColor = Color.White
        )
    }
}

@Preview
@Composable
fun LoadingPreview() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(PaddingValues(0.dp, 0.dp, 0.dp, 16.dp)),
        contentAlignment = Alignment.Center) {
        LazyColumn (
            modifier = Modifier.padding(0.dp)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 所有加载动画项
            item { Text("Circular Loading", style = MaterialTheme.typography.titleMedium) }
            item { LoadingIndicator() }

            item { Text("Pulse Dot Loading", style = MaterialTheme.typography.titleMedium) }
            item { PulseDotLoading() }

            item { Text("Rotating Ring Loading", style = MaterialTheme.typography.titleMedium) }
            item { RotatingRingLoading() }

            item { Text("Wave Loading", style = MaterialTheme.typography.titleMedium) }
            item { WaveLoading() }

            item { Text("Spinner Loading", style = MaterialTheme.typography.titleMedium) }
            item { SpinnerLoading() }

            item { Text("Bouncing Dots Loading", style = MaterialTheme.typography.titleMedium) }
            item { BouncingDotsLoading() }

            item { Text("Rotating Squares Loading", style = MaterialTheme.typography.titleMedium) }
            item { RotatingSquaresLoading() }

            item { Text("Progress Bar Loading", style = MaterialTheme.typography.titleMedium) }
            item { ProgressBarLoading() }

            item { Text("Chasing Dots Loading", style = MaterialTheme.typography.titleMedium) }
            item { ChasingDotsLoading() }

            item { Text("Folding Cube Loading", style = MaterialTheme.typography.titleMedium) }
            item { FoldingCubeLoading() }

            item { Text("Chrysanthemum Loading", style = MaterialTheme.typography.titleMedium) }
            item { ChrysanthemumLoading() }

            item { // 基础样式
                IosLoadingIndicator()}
            item {  // 小尺寸
                IosLoadingIndicator(
                    size = 30.dp,
                    strokeWidth = 2.dp,
                    text = "加载中..."
                ) }


            item {  // 深色模式样式
                IosLoadingIndicator(
                    color = Color.White,
                    backgroundColor = Color.Black.copy(alpha = 0.7f),
                    text = "处理中",
                    textColor = Color.White.copy(alpha = 0.8f)
                ) }

            item { // 自定义颜色
                IosLoadingIndicator(
                    color = Color(0xFF34C759), // iOS 绿色
                    strokeWidth = 4.dp,
                    size = 50.dp,
                    text = "保存中..."
                ) }

        }
    }
}