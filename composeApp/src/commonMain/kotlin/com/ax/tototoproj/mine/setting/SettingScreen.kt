package com.ax.tototoproj.mine.setting

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ax.tototoproj.list.ListItem
import com.ax.tototoproj.mine.profile.FunctionItem
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import cafe.adriel.voyager.navigator.Navigator
import com.ax.tototoproj.mine.profile.ProfileScreen

/**
 * @author : lihuiwu
 * @date : 2025/8/11
 * @desc : 炫酷设置页面
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
class SettingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var darkMode by remember { mutableStateOf(false) }
        var selectedTheme by remember { mutableStateOf(0) }
        var notificationEnabled by remember { mutableStateOf(false) }
        var volumeLevel by remember { mutableStateOf(0.7f) }

        // 添加状态变化日志
        LaunchedEffect(darkMode, selectedTheme) {
            println("主题状态变化: darkMode=$darkMode, themeIndex=$selectedTheme")
        }

        // 动态计算颜色方案
        val colorScheme = remember(darkMode, selectedTheme) {
            val colors = when (selectedTheme) {
                0 -> listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                1 -> listOf(Color(0xFFFF416C), Color(0xFFFF4B2B))
                else -> listOf(Color(0xFF11998E), Color(0xFF38EF7D))
            }

            if (darkMode) {
                darkColorScheme(
                    primary = colors[0],
                    secondary = colors[1],
                    surface = Color(0xFF121212),
                    background = Color(0xFF000000)
                )
            } else {
                lightColorScheme(
                    primary = colors[0],
                    secondary = colors[1],
                    surface = Color.White,
                    background = Color(0xFFF5F5F5)
                )
            }
        }

        MaterialTheme(colorScheme = colorScheme) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "设置",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navigator.pop() }) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "返回",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // 主题选择
                        item {
                            GlassCard {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        "主题颜色",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        listOf(
                                            listOf(Color(0xFF6A11CB), Color(0xFF2575FC)), // 蓝紫
                                            listOf(Color(0xFFFF416C), Color(0xFFFF4B2B)), // 红橙
                                            listOf(Color(0xFF11998E), Color(0xFF38EF7D))  // 绿
                                        ).forEachIndexed { index, colors ->
                                            ThemeColorItem(
                                                colors = colors,
                                                isSelected = index == selectedTheme,
                                                onClick = { selectedTheme = index }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // 深色模式开关
                        item {
                            GlassCard {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .clickable { darkMode = !darkMode },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DarkMode,
                                        contentDescription = "深色模式",
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "深色模式",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                    Switch(
                                        checked = darkMode,
                                        onCheckedChange = { darkMode = it },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                                            uncheckedThumbColor = Color(0xFFAAAAAA)
                                        )
                                    )
                                }
                            }
                        }

                        // 设置项列表
                        items(getSettingItems(navigator)) { item ->
                            GlassCard {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .clickable { item.onClick() },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = item.title,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        if (item.description != null) {
                                            Text(
                                                text = item.description,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }

                                    when {
                                        item.isSwitch -> {
                                            Switch(
                                                checked = when (item.title) {
                                                    "通知" -> notificationEnabled
                                                    else -> false
                                                },
                                                onCheckedChange = {
                                                    when (item.title) {
                                                        "通知" -> notificationEnabled = it
                                                    }
                                                },
                                                colors = SwitchDefaults.colors(
                                                    checkedThumbColor = if (darkMode) Color(0xFFBB86FC) else MaterialTheme.colorScheme.primary,
                                                    uncheckedThumbColor = Color(0xFFAAAAAA),
                                                    checkedTrackColor = Color(0x556A11CB),
                                                    uncheckedTrackColor = Color(0x55333333)
                                                )
                                            )
                                        }
                                        item.isSlider -> {
                                            Slider(
                                                value = volumeLevel,
                                                onValueChange = { volumeLevel = it },
                                                valueRange = 0f..1f,
                                                modifier = Modifier.width(120.dp),
                                                colors = SliderDefaults.colors(
                                                    thumbColor = MaterialTheme.colorScheme.primary,
                                                    activeTrackColor = MaterialTheme.colorScheme.primary
                                                )
                                            )
                                        }
                                        else -> Icon(
                                            Icons.Default.ArrowBack,
                                            contentDescription = "更多",
                                            modifier = Modifier
                                                .size(20.dp)
                                                .graphicsLayer {
                                                    rotationY = 180f
                                                },
                                            tint = Color.Gray
                                        )
                                    }
                                }
                            }
                        }

                        // 关于应用
                        item {
                            GlassCard {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        "关于应用",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "版本: 1.0.0",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        "开发者: 张三",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        "© 2025 ToToToProj",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 玻璃态卡片组件
    @Composable
    fun GlassCard(content: @Composable () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
            ) {
                content()
            }
        }
    }

    // 主题颜色选择项
    @Composable
    fun ThemeColorItem(colors: List<Color>, isSelected: Boolean, onClick: () -> Unit) {
        val animatedSize by animateFloatAsState(
            targetValue = if (isSelected) 1.1f else 1f,
            animationSpec = tween(300)
        )

        Box(
            modifier = Modifier
                .size(50.dp)
                .graphicsLayer {
                    scaleX = animatedSize
                    scaleY = animatedSize
                }
                .clip(CircleShape)
                .background(
                    brush = Brush.verticalGradient(colors),
                    shape = CircleShape
                )
                .clickable(onClick = onClick)
                .border(
                    width = if (isSelected) 3.dp else 0.dp,
                    color = Color.White,
                    shape = CircleShape
                )
        )
    }

    // 设置项数据类
    data class SettingItem(
        val title: String,
        val icon: ImageVector,
        val description: String? = null,
        val isSwitch: Boolean = false,
        val isSlider: Boolean = false,
        val onClick: () -> Unit = {}
    )

    // 获取设置项列表
    fun getSettingItems(navigator: Navigator): List<SettingItem> {
        return listOf(
            SettingItem(
                title = "账号设置",
                icon = Icons.Default.Person,
                onClick = { /* 跳转账号设置 */
                    navigator.push(ProfileScreen())
                }
            ),
            SettingItem(
                title = "通知",
                icon = Icons.Default.Notifications,
                isSwitch = true
            ),
            SettingItem(
                title = "音量",
                icon = Icons.Default.VolumeUp,
                isSlider = true
            ),
            SettingItem(
                title = "隐私设置",
                icon = Icons.Default.Lock,
                onClick = { /* 跳转隐私设置 */ }
            ),
            SettingItem(
                title = "语言",
                icon = Icons.Default.Language,
                description = "简体中文",
                onClick = { /* 跳转语言设置 */ }
            ),
            SettingItem(
                title = "存储管理",
                icon = Icons.Default.Storage,
                onClick = { /* 跳转存储管理 */ }
            ),
            SettingItem(
                title = "主题定制",
                icon = Icons.Default.Palette,
                onClick = { /* 跳转主题定制 */ }
            )
        )
    }
}