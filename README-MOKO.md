ToT0Toproj/
├── androidApp/         # Android 专属应用模块
├── iosApp/             # iOS 专属应用模块
└── composeApp/         # 跨平台共享模块
├── src/
│   ├── commonMain/
│   │   ├── kotlin/ # 共享代码
│   │   └── resources/  # MOKO 资源目录 (关键!)
│   │       └── MR/
│   │           ├── base/
│   │           │   ├── strings.xml
│   │           │   ├── plurals.xml
│   │           │   └── images/
│   │           │       └── logo.png
│   │           ├── en/
│   │           └── zh/
│   ├── androidMain/ # Android 平台代码
│   └── iosMain/     # iOS 平台代码
└── build.gradle.kts


ios build phase 配置
# Type a script or drag a script file from your workspace to insert its path.
"$SRCROOT/../gradlew" -p "$SRCROOT/../" :composeApp:copyFrameworkResourcesToApp \
-Pmoko.resources.PLATFORM_NAME="$PLATFORM_NAME" \
-Pmoko.resources.CONFIGURATION="${KOTLIN_FRAMEWORK_BUILD_TYPE:-$CONFIGURATION}" \
-Pmoko.resources.ARCHS="$ARCHS" \
-Pmoko.resources.BUILT_PRODUCTS_DIR="$BUILT_PRODUCTS_DIR" \
-Pmoko.resources.CONTENTS_FOLDER_PATH="$CONTENTS_FOLDER_PATH"
