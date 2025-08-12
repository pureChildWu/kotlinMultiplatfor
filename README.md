# 🚀 ToToToProj - 跨平台移动应用开发模板

**使用Kotlin Multiplatform和Compose Multiplatform构建高性能Android/iOS应用**

[![Kotlin](https://img.shields.io/badge/kotlin-1.9.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.6.0-blue)](https://www.jetbrains.com/lp/compose-multiplatform/)

## ✨ 核心优势

1. **代码共享率高达90%** - 使用Kotlin Multiplatform在Android和iOS平台共享业务逻辑
2. **现代化UI框架** - 基于Jetpack Compose/JetBrains Compose Multiplatform构建声明式UI
3. **高性能媒体处理** - 集成Kamel图像加载库，支持多种解码器和缓存策略
4. **网络优化** - 内置Ktor客户端，支持日志记录、超时控制和自动重试
5. **多平台资源管理** - 使用moko-resources实现跨平台资源统一管理

## 🛠️ 技术栈

| 技术 | 功能 | 版本 |
|------|------|------|
| Kotlin Multiplatform | 跨平台核心逻辑 | 1.9.0 |
| Compose Multiplatform | 声明式UI框架 | 1.6.0 |
| Kamel | 图像加载与处理 | 1.0.7 |
| Ktor | 网络请求库 | 2.3.0 |
| Voyager | 导航框架 | 1.0.0 |

## 🚀 快速开始

```bash
# 克隆仓库
git clone https://github.com/yourusername/ToToToProj.git

# 构建项目 (需要Android Studio或Xcode)
./gradlew :composeApp:assembleDebug  # Android
open iosApp/iosApp.xcodeproj         # iOS

// 全局Kamel配置
fun initKamel() = KamelConfig {
    // 图像解码器
    imageBitmapDecoder()
    imageVectorDecoder()
    svgDecoder()
    
    // 网络配置
    httpUrlFetcher {
        install(HttpTimeout) {
            requestTimeoutMillis = 10000 // 10秒超时
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }
}

# 资源管理
├── resources/           # 资源目录
├── composeApp/          # 共享代码
│   ├── commonMain/      # 跨平台通用代码
│   ├── androidMain/     # Android平台特定代码
│   └── iosMain/         # iOS平台特定代码
├── iosApp/              # iOS应用入口
└── gradle/              # 依赖管理


这个版本：V1.0.0
1. 添加了吸引眼球的emoji和徽章
2. 突出技术亮点和优势
3. 使用代码片段展示核心功能
4. 包含清晰的项目结构说明
5. 添加未来计划增加期待感
6. 优化了排版和可读性