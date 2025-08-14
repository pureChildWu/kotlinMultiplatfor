my-kmp-project/
├── build-logic/                # 构建逻辑约定插件
│   ├── conventions/
│   │   ├── build.gradle.kts
│   │   └── src/main/kotlin/
│   │       ├── android-conventions.gradle.kts
│   │       ├── kmp-conventions.gradle.kts
│   │       └── compose-conventions.gradle.kts
│   └── settings.gradle.kts
│
├── gradle/
│   └── libs.versions.toml       # 统一依赖版本管理
│
├── core/                       # 核心共享模块
│   ├── src/
│   │   ├── commonMain/         # 公共代码
│   │   │   ├── di/            # 依赖注入
│   │   │   ├── extensions/    # 扩展函数
│   │   │   ├── utils/         # 工具类
│   │   │   └── Config.kt      # 配置常量
│   │   ├── androidMain/        # Android 平台特定代码
│   │   └── iosMain/            # iOS 平台特定代码
│   └── build.gradle.kts
│
├── data/                       # 数据层模块
│   ├── src/
│   │   ├── commonMain/
│   │   │   ├── local/         # 本地数据源
│   │   │   ├── remote/        # 远程数据源
│   │   │   ├── repository/    # 数据仓库
│   │   │   └── di/DataModule.kt
│   │   ├── androidMain/
│   │   └── iosMain/
│   └── build.gradle.kts
│
├── domain/                     # 领域层模块
│   ├── src/
│   │   └── commonMain/
│   │       ├── model/         # 领域模型
│   │       ├── repository/    # 仓库接口
│   │       ├── usecase/       # 业务用例
│   │       └── di/DomainModule.kt
│   └── build.gradle.kts
│
├── features/                   # 功能模块（按功能划分）
│   ├── feature-auth/          # 认证功能
│   │   ├── src/
│   │   │   ├── commonMain/
│   │   │   │   ├── presentation/ # MVVM 状态管理
│   │   │   │   └── di/AuthModule.kt
│   │   │   ├── androidMain/     # Android UI
│   │   │   └── iosMain/         # iOS UI
│   │   └── build.gradle.kts
│   │
│   └── feature-home/          # 主页功能
│       ├── src/
│       │   ├── commonMain/
│       │   │   ├── presentation/
│       │   │   └── di/HomeModule.kt
│       │   ├── androidMain/
│       │   └── iosMain/
│       └── build.gradle.kts
│
├── ui/                         # UI 共享模块
│   ├── src/
│   │   ├── commonMain/
│   │   │   ├── components/    # 公共组件
│   │   │   ├── theme/         # 主题定义
│   │   │   └── navigation/    # 导航管理
│   │   ├── androidMain/
│   │   └── iosMain/
│   └── build.gradle.kts
│
├── app/                        # 应用入口
│   ├── androidApp/            # Android 应用
│   │   ├── src/main/
│   │   │   ├── AndroidManifest.xml
│   │   │   └── kotlin/
│   │   │       └── MainActivity.kt
│   │   └── build.gradle.kts
│   │
│   └── iosApp/                # iOS 应用
│       ├── iosApp/
│       │   ├── iosApp.xcodeproj
│       │   └── iosApp/
│       │       ├── ContentView.swift
│       │       └── iosAppApp.swift
│       └── build.gradle.kts
│
├── build.gradle.kts            # 根项目构建配置
└── settings.gradle.kts         # 项目设置