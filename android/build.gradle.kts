import com.android.build.gradle.api.AndroidBasePlugin
import com.github.megatronking.stringfog.plugin.StringFogExtension
import tornaco.project.android.thanox.Configs
import tornaco.project.android.thanox.Configs.outDir
import tornaco.project.android.thanox.Configs.thanoxVersionCode
import tornaco.project.android.thanox.Configs.thanoxVersionName
import tornaco.project.android.thanox.log

buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath(libs.stringfog.plugin)
        classpath(libs.stringfog.xor)
    }
}

plugins {
    alias(libs.plugins.agp.lib) apply false
    alias(libs.plugins.agp.app) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.protobuf.gradle.plugin) apply false
    alias(libs.plugins.gmazzo.buildconfig) apply false
    alias(libs.plugins.gladed.androidgitversion) apply true
    alias(libs.plugins.compose.compiler) apply false
    id("thanox-proj")
}

androidGitVersion {
    prefix = "v"
    codeFormat = "MNPBB"
}

val legacyCodeBase = 2000000
val startOf2000 = 946684800000L
val versionCodeByTime = ((System.currentTimeMillis() - startOf2000) / 1000 / 60 / 10).toInt() + legacyCodeBase
thanoxVersionCode = versionCodeByTime
thanoxVersionName = androidGitVersion.name()

val androidSourceCompatibility by extra(JavaVersion.VERSION_21)
val androidTargetCompatibility by extra(JavaVersion.VERSION_21)

subprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(arrayOf("-Xmaxerrs", "1000"))
        options.encoding = "UTF-8"
    }

    plugins.withType(com.android.build.gradle.api.AndroidBasePlugin::class.java) {
        extensions.configure(com.android.build.api.dsl.CommonExtension::class.java) {
            compileSdk = Configs.compileSdkVersion
            ndkVersion = Configs.ndkVersion

            defaultConfig {
                minSdk = Configs.minSdkVersion
                // 💡 關鍵修復：解決子模組渠道匹配問題
                missingDimensionStrategy("market", "prc")

                if (this is com.android.build.api.dsl.ApplicationDefaultConfig) {
                    targetSdk = Configs.targetSdkVersion
                    versionCode = Configs.thanoxVersionCode
                    versionName = Configs.thanoxVersionName
                }
            }

            compileOptions {
                sourceCompatibility = androidSourceCompatibility
                targetCompatibility = androidTargetCompatibility
            }
        }
    }

    buildDir = file("${outDir}${path.replace(":", File.separator)}")
}

rootProject.buildDir = outDir
