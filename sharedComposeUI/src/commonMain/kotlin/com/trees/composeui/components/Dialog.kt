package com.trees.composeui.components

/**
 * @author : lihuiwu
 * @date : 2025/8/19
 * @desc :
 */
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * @author : lihuiwu
 * @date : 2025/8/19
 * @desc : 对话框组件库
 */

// 1. 基础确认对话框
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "确认",
    dismissText: String = "取消"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, fontWeight = FontWeight.Bold) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = dismissText)
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}

// 2. 警告对话框（单按钮）
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    buttonText: String = "确定"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, fontWeight = FontWeight.Bold) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = buttonText)
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}

// 3. 选择对话框
@Composable
fun SelectionDialog(
    title: String,
    options: List<String>,
    onOptionSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                options.forEachIndexed { index, option ->
                    Button(
                        onClick = { onOptionSelected(index) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(text = option)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "取消")
                }
            }
        }
    }
}

// 4. 输入对话框
// 4. 输入对话框
@Composable
fun InputDialog(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    placeholder: String = "请输入"
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // 使用真正的输入框
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .defaultMinSize(48.dp),
                    placeholder = { Text(placeholder) },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "取消")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onConfirm,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "确定")
                    }
                }
            }
        }
    }
}

// 5. 进度对话框
@Composable
fun ProgressDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 进度指示器 - 使用基础组件模拟
                Surface(
                    modifier = Modifier
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentHeight(Alignment.CenterVertically),
//                        .width(48.dp)
//                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = "加载中...",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = message)
            }
        }
    }
}

// 6. 基本底部弹窗
@Composable
fun BottomSheetDialog(
    title: String,
    content: @Composable () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "确定",
    dismissText: String = "取消"
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        // 使用 Box 将内容定位在底部
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp), // 顶部留出空间，使内容在底部
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth() // 宽度充满屏幕
                    .padding(horizontal = 0.dp), // 移除水平边距
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    content()

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text(text = dismissText)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = confirmText)
                        }
                    }
                }
            }
        }
    }
}

// 7. 列表底部弹窗
@Composable
fun ListBottomSheet(
    title: String,
    items: List<String>,
    onItemSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        // 使用 Box 将内容定位在底部
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp), // 顶部留出空间，使内容在底部
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth() // 宽度充满屏幕
                    .padding(horizontal = 0.dp), // 移除水平边距
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    items.forEachIndexed { index, item ->
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onItemSelected(index) }
                                .padding(vertical = 12.dp)
                        )
                        if (index < items.size - 1) {
                            Divider(
                                thickness = 0.5.dp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

// 8. 全屏底部弹窗
@Composable
fun FullScreenBottomSheet(
    content: @Composable () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // 内容区域
                content()

                // 关闭按钮
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "关闭",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// 预览函数
@Composable
fun DialogPreview() {
    var showConfirm by remember { mutableStateOf(false) }
    var showAlert by remember { mutableStateOf(false) }
    var showSelection by remember { mutableStateOf(false) }
    var showInput by remember { mutableStateOf(false) }
    var showProgress by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = { showConfirm = true }) {
            Text(text = "显示确认对话框")
        }

        Button(onClick = { showAlert = true }) {
            Text(text = "显示警告对话框")
        }

        Button(onClick = { showSelection = true }) {
            Text(text = "显示选择对话框")
        }

        Button(onClick = { showInput = true }) {
            Text(text = "显示输入对话框")
        }

        Button(onClick = { showProgress = true }) {
            Text(text = "显示进度对话框")
        }
    }

    if (showConfirm) {
        ConfirmDialog(
            title = "确认操作",
            message = "您确定要执行此操作吗？此操作不可撤销。",
            onConfirm = { showConfirm = false },
            onDismiss = { showConfirm = false }
        )
    }

    if (showAlert) {
        AlertDialog(
            title = "操作成功",
            message = "您的操作已成功完成！",
            onDismiss = { showAlert = false }
        )
    }

    if (showSelection) {
        SelectionDialog(
            title = "选择选项",
            options = listOf("选项一", "选项二", "选项三"),
            onOptionSelected = { index ->
                println("选择了选项: ${index + 1}")
                showSelection = false
            },
            onDismiss = { showSelection = false }
        )
    }

    if (showInput) {
        var inputValue by remember { mutableStateOf("") }
        InputDialog(
            title = "输入信息",
            value = inputValue,
            onValueChange = { inputValue = it },
            onConfirm = {
                println("输入内容: $inputValue")
                showInput = false
            },
            onDismiss = { showInput = false }
        )
    }

    if (showProgress) {
        ProgressDialog(
            title = "处理中",
            message = "请稍候，正在处理您的请求...",
            onDismiss = { showProgress = false }
        )
    }

    // ... 原有状态变量 ...
    var showBottomSheet by remember { mutableStateOf(false) }
    var showListSheet by remember { mutableStateOf(false) }
    var showFullScreenSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ... 原有按钮 ...

        Button(onClick = { showBottomSheet = true }) {
            Text(text = "显示底部弹窗")
        }

        Button(onClick = { showListSheet = true }) {
            Text(text = "显示列表底部弹窗")
        }

        Button(onClick = { showFullScreenSheet = true }) {
            Text(text = "显示全屏底部弹窗")
        }
    }

    // ... 原有对话框 ...

    if (showBottomSheet) {
        BottomSheetDialog(
            title = "操作确认",
            onDismiss = { showBottomSheet = false },
            content = {
                Text(
                    text = "您确定要执行此操作吗？此操作可能会影响您的账户安全。",
                    style = MaterialTheme.typography.bodyMedium
                )
            },

        )
    }

    if (showListSheet) {
        ListBottomSheet(
            title = "选择选项",
            items = listOf("选项一", "选项二", "选项三", "选项四"),
            onItemSelected = { index ->
                println("选择了: ${index + 1}")
                showListSheet = false
            },
            onDismiss = { showListSheet = false }
        )
    }

    if (showFullScreenSheet) {
        FullScreenBottomSheet(
            onDismiss = { showFullScreenSheet = false },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "全屏内容区域",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    Text(
                        text = "这里可以放置任何内容，如表单、图片或复杂布局",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
    }
}