package com.ax.tototoproj

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ax.tototoproj.main.MainScreen

/**
 * 登录屏幕实现类，实现 Voyager 的 Screen 接口
 *
 * 功能特点：
 * 1. 渐变背景设计
 * 2. 动画效果（淡入、缩放）
 * 3. 防止软键盘覆盖内容
 * 4. 响应式布局
 * 5. 键盘导航支持
 * 6. 密码可见性切换
 * 7. 输入验证
 */
@OptIn(ExperimentalComposeUiApi::class)
class LoginScreen : Screen {

    /**
     * 登录页面的主要内容
     */
    @Composable
    override fun Content() {
        // 获取导航器实例，用于页面跳转
        val navigator = LocalNavigator.currentOrThrow

        // 用户名和密码的状态管理
        var username by remember { mutableStateOf("a") }
        var password by remember { mutableStateOf("a") }

        // 密码可见性状态
        var passwordVisible by remember { mutableStateOf(false) }

        // 键盘控制相关工具
        val keyboardController = LocalSoftwareKeyboardController.current
        val passwordFocus = remember { FocusRequester() }
        val scrollState = rememberScrollState()

        // 添加错误状态
        var usernameError by remember { mutableStateOf(false) }
        var passwordError by remember { mutableStateOf(false) }

        // 创建渐变背景
        val gradient = Brush.verticalGradient(
            colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC)),
            startY = 0f,
            endY = 1000f
        )

        // 主布局：使用Box作为容器，设置渐变背景
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
        ) {
            // 内容列：包含所有UI元素
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState) // 支持滚动
                    .imePadding() // 防止软键盘覆盖内容
                    .padding(horizontal = 30.dp), // 左右30dp边距
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 标题 - 淡入动画效果
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(800))
                ) {
                    Text(
                        "欢迎回来",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 40.dp)
                    )
                }

                // 用户名输入框 - 添加错误提示
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            usernameError = false // 输入时清除错误状态
                        },
                        label = { Text("用户名", color = Color.White.copy(alpha = 0.8f)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "用户名",
                                tint = Color.White.copy(alpha = 0.8f)
                            )
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() }),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedIndicatorColor = if (usernameError) Color.Red else Color.White.copy(alpha = 0.5f),
                            focusedIndicatorColor = if (usernameError) Color.Red else Color.White
                        ),
                        isError = usernameError, // 设置错误状态
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    // 错误提示文本
                    if (usernameError) {
                        Text(
                            "请输入用户名",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 密码输入框 - 添加错误提示
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = false // 输入时清除错误状态
                        },
                        label = { Text("密码", color = Color.White.copy(alpha = 0.8f)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "密码",
                                tint = Color.White.copy(alpha = 0.8f)
                            )
                        },
                        maxLines = 1,
                        visualTransformation = if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                        trailingIcon = {
                            IconButton(
                                onClick = { passwordVisible = !passwordVisible }
                            ) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisible) "隐藏密码" else "显示密码",
                                    tint = Color.White.copy(alpha = 0.8f)
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedIndicatorColor = if (passwordError) Color.Red else Color.White.copy(alpha = 0.5f),
                            focusedIndicatorColor = if (passwordError) Color.Red else Color.White
                        ),
                        isError = passwordError, // 设置错误状态
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(passwordFocus)
                    )

                    // 错误提示文本
                    if (passwordError) {
                        Text(
                            "请输入密码",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 登录按钮 - 更新验证逻辑
                AnimatedVisibility(
                    visible = true,
                    enter = scaleIn(animationSpec = tween(500, delayMillis = 300))
                ) {
                    Button(
                        onClick = {
                            // 验证输入是否为空
                            usernameError = username.isBlank()
                            passwordError = password.isBlank()

                            if (!usernameError && !passwordError) {
                                // 登录验证逻辑
                                navigator.replaceAll(MainScreen())
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .shadow(8.dp, RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF6A11CB)
                        )
                    ) {
                        Text(
                            "登录",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}