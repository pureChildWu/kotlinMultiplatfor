package com.ax.tototoproj

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("AppDebug", "Application created")

        // 添加设备信息日志
        Log.d("AppDebug", "Manufacturer: ${Build.MANUFACTURER}")
        Log.d("AppDebug", "Model: ${Build.MODEL}")
        Log.d("AppDebug", "SDK: ${Build.VERSION.SDK_INT}")

        // 检查权限状态
        Log.d("AppDebug", "Checking permission states...")

        // 小米设备特殊处理
//        if (Build.MANUFACTURER.equals("Xiaomi", ignoreCase = true)) {
//            Log.d("AppDebug", "Xiaomi device detected")
//            requestXiaomiPermissions()
//        }
    }

    private fun requestXiaomiPermissions() {
        try {
            // 请求悬浮窗权限
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:$packageName")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            Log.d("AppDebug", "Requested overlay permission")

            // 请求忽略电池优化
            val powerIntent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            powerIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(powerIntent)
            Log.d("AppDebug", "Requested battery optimization ignore")
        } catch (e: Exception) {
            Log.e("AppDebug", "Failed to request Xiaomi permissions", e)
        }
    }
}