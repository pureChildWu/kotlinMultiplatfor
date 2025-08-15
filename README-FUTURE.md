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


- :core
    - 无其他模块依赖（除了外部库）

- :domain
    - 依赖:core（可选，如果domain中使用了core中的工具或扩展）

- :data
    - 依赖:domain（必须，因为要实现domain中的仓库接口）
    - 依赖:core（使用工具类等）

- :ui
    - 依赖:core（可能使用其中的工具）
    - 注意：ui模块不应该依赖domain，因为这样会导致domain的模型变化影响ui，而且ui模块应该是纯组件，不包含业务逻辑。所以，ui模块只提供可组合函数，参数为基本类型或ui model（在ui模块内定义）。因此，ui模块不依赖domain。

- :features/feature-xxx
    - 依赖:domain（使用模型、用例、仓库接口）
    - 依赖:data（因为需要获取仓库实现，但注意：在KMP中，我们通常通过依赖注入将data层的仓库实现注入到feature，所以feature需要知道data模块？或者，我们可以通过依赖注入框架在app模块中连接，这样feature模块只需要依赖domain接口，而不需要依赖data实现。但是，为了在feature中使用具体实现，我们有两种选择：
      a) 让feature模块依赖data模块，这样可以直接使用data模块中的实现。
      b) 不直接依赖data模块，而是通过依赖注入框架（如Koin）在app模块中提供实现，这样feature模块只需要依赖domain接口，而不需要知道data模块。但是，在KMP中，由于我们要在commonMain中编写代码，而commonMain不能包含平台相关代码，所以通常我们在data模块中提供实现，然后在feature中通过接口使用，而具体的实现实例由平台相关的依赖注入模块（在app模块中）提供。因此，feature模块不需要直接依赖data模块，只需要依赖domain。但是，在构建时，我们需要将data模块作为依赖注入模块的一部分，所以实际上在app模块中会同时包含feature和data模块。

  根据项目结构，我建议：feature模块只依赖domain和ui，不直接依赖data。data模块由app模块引入，并通过依赖注入框架注册。这样，feature模块只通过接口与数据层交互，实现解耦。

    - 依赖:ui（使用公共组件）

- :app (androidApp和iosApp)
    - 依赖:features（包含所有需要的功能模块）
    - 依赖:data（因为app模块需要提供数据层的实现，并注册到依赖注入容器）
    - 依赖:domain（因为依赖注入需要注册domain的接口和data的实现）
    - 依赖:core（基础配置）
    - 依赖:ui（公共UI组件）

整体依赖关系图
app (Android/iOS)
├── features/feature-auth
├── features/feature-home
│   ├── domain
│   │   ├── data
│   │   │   └── core
│   │   └── ui
│   └── core
└── ui
