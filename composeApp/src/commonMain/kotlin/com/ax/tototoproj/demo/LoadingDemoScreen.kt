package com.ax.tototoproj.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.trees.composeui.components.FullScreenLoading
import com.trees.composeui.components.LoadingPreview
import com.trees.composeui.components.TitleBack

/**
 * @author : lihuiwu
 * @date : 2025/8/19
 * @desc :
 */
class LoadingDemoScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column {
            TitleBack(
                title = "示例标题",
                onBackClick = { navigator.pop() }
            )
            LoadingPreview()
        }

        FullScreenLoading()
    }
}