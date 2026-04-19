@file:Suppress("UnstableApiUsage")

rootProject.name = "Thanox"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/public/")
        maven(url = "https://jitpack.io")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        // jcenter() // 建議註釋掉，這東西已經關閉了，會拖慢編譯
    }
}

// --- 基礎模組 ---
include(":app")
include(":shortcut_stub")
include(":annotation_processors:xposed_hook_annotation")
include(":annotation_processors:xposed_hook_compiler")

// --- 開源模組 (保持默認路徑) ---
include(":modules:module_common")
include(":modules:module_filepicker")
include(":modules:module_sub")
include(":modules:module_feature_launcher")
include(":modules:module_bpok")

// --- 第三方依賴 ---
include(":third_party:dateformatter")
include(":third_party:recyclerview-fastscroll")
include(":third_party:search")
include(":third_party:apkbuilder")
include(":third_party:time-duration-picker")
include(":third_party:remix")
include(":third_party:compose-color-picker")
include(":third_party:reorderable")
include(":third_party:libxposed:interface")
include(":third_party:libxposed:service")
include(":third_party:libxposed:api")

// --- Android 框架層 (使用本地路徑) ---
include(":android_framework:base")
include(":android_framework:res")
include(":android_framework:patchx-lib")
include(":android_framework:hidden-api")
include(":android_framework:dex-maker")
include(":android_framework:patch-common")
include(":android_framework:db")
include(":android_framework:services")
include(":android_framework:utils")
include(":android_framework:patchx-entry")

// --- Magisk 相關 ---
include(":android_framework:patch-magisk:module")
include(":android_framework:patch-magisk:bridge")
include(":android_framework:patch-magisk:bridge-dex-app")
include(":android_framework:patch-magisk:patch-framework")

// --- Shizuku 相關 ---
include(":shizuku:core")
include(":shizuku:services")
include(":shizuku:ui")
include(":shizuku:app")

// 💡 關鍵：刪除了最後那段 internalProjects.forEach { ... } 邏輯
// 這樣 Gradle 就不會去 internal/Thanox-Internal 目錄找代碼了。
