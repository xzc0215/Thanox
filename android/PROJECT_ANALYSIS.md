# Thanox Android Project Analysis

## Project Overview

**Thanox** is an advanced Android system optimization and control application, similar to Greenify but with more comprehensive features.

- **Name**: Thanox
- **Type**: Android system optimization tool
- **Author**: Tornaco
- **License**: Open source (GitHub)
- **Current Version**: ~8.8 (based on git tags)

## Architecture & Tech Stack

### Build System
- Gradle Kotlin DSL with Version Catalogs
- Android Gradle Plugin 8.10.0
- Kotlin 2.1.20
- Java 21

### Key Technologies
- **UI**: Jetpack Compose, Material 3, ViewBinding, DataBinding
- **DI**: Dagger Hilt
- **Reactive**: RxJava2, Kotlin Coroutines
- **State Management**: Orbit MVI
- **Database**: Room
- **Networking**: Retrofit, OkHttp
- **Native**: NDK (r29), CMake, Zygisk native hooks

## Project Structure

```
android/
├── app/                      # Main application
├── modules/                  # Feature modules
│   ├── module_common/       # Common utilities
│   ├── module_filepicker/   # File picker
│   ├── module_sub/          # Subscription features (internal)
│   └── module_feature_launcher/ # Launcher features (internal)
├── android_framework/        # Android framework modifications
│   ├── patch-magisk/        # Magisk/Zygisk integration
│   ├── patchx-lib/          # Custom patching library
│   ├── services/            # Framework service hooks (API 28-35)
│   └── hidden-api/          # Hidden API access
├── shizuku/                  # Shizuku integration (internal)
├── third_party/             # Third-party libraries
│   └── libxposed/          # Xposed framework hooks
├── annotation_processors/   # Custom annotation processing
├── buildSrc/                # Custom build plugins
└── internal/                # Internal/private modules
```

## Key Features

1. **App Management**: Batch operations, activity control
2. **Notification Management**: Notification recording, stats, history
3. **System Hooks**: Zygisk/Magisk module for system-level control
4. **Shizuku Support**: Alternative to root via Shizuku
5. **Xposed Integration**: Custom hooking framework
6. **Multi-flavor**: ROW (Rest of World) and PRC (China) builds

## Build Configuration

| Setting | Value |
|---------|-------|
| Compile SDK | 35 |
| Min SDK | 24 |
| Target SDK | 35 |
| NDK Version | 29.0.14206865 |
| Build Tools | 30.0.3 |

### Flavors
- `row` - Rest of World (global)
- `prc` - People's Republic of China

### Build Types
- Debug
- Release with ProGuard obfuscation

### Versioning
Time-based version codes calculated from year 2000 epoch with legacy offset (2000000).

### Signing
Custom keystore located at `environment/keys/android.jks`

## Notable Implementation Details

1. **String Obfuscation**: Uses StringFog with XOR encryption for sensitive strings
2. **Random Package Names**: Generates random package names for ProGuard repackage
3. **Magisk Module Integration**: Automatically packages Magisk module into APK resources
4. **Multi-API Support**: Framework patches for Android 28-35
5. **16KB Page Size**: NDK r29+ for Android 16KB page size support

## Internal vs External

The project has an `internal/Thanox-Internal` directory containing:
- Premium/subscription features (`module_sub`, `module_feature_launcher`)
- Shizuku implementation
- Additional framework service hooks

These are likely private/pro features not in the public repo.

## Development Status

- **Active Development**: Recent commits show ongoing UI improvements and bug fixes
- **CI/CD**: Build scripts for automated releases (`scripts/build_*.sh`)
- **Testing**: AndroidX Test, Espresso, UIAutomator configured
- **Documentation**: Minimal (TODO exists, docs folder empty)

## Dependencies Highlights

- 200+ dependencies including AndroidX, Compose BOM, Dagger Hilt
- Custom Xposed hook compiler
- LibSU for root access
- Chucker for debugging (debug builds only)

### Major Libraries

| Category | Libraries |
|----------|-----------|
| UI | Compose BOM, Material 3, Accompanist, Lottie |
| DI | Dagger Hilt |
| Database | Room |
| Networking | Retrofit, OkHttp, Gson |
| Reactive | RxJava2, Kotlin Coroutines |
| State | Orbit MVI |
| Root | LibSU, Shizuku |
| Security | StringFog |

## Build Scripts

Located in `scripts/`:
- `build_magisk_patch.sh` - Build Magisk patch
- `build_prc_release.sh` - Build PRC release
- `build_row_release.sh` - Build ROW release

## TODO Items

1. Move all `strings.xml` to a single module
2. Test HTTP cache
3. Test Shizuku SF pager
4. Test Compose Switch

## Conclusion

This is a sophisticated Android system tool with deep framework integration, requiring root or Shizuku for full functionality. The project demonstrates advanced Android development practices including:

- Multi-module architecture
- Custom build plugins
- Native code integration
- Framework-level hooking
- Obfuscation and security measures

---
*Generated: 2026-02-28*

---

## Internal/Private Modules Analysis

The `internal/Thanox-Internal` directory contains proprietary modules that are not part of the public repository. These modules provide premium features, subscription management, and advanced system integration.

### Directory Structure

```
internal/Thanox-Internal/
├── modules/
│   ├── module_sub/              # Subscription/Donation module
│   └── module_feature_launcher/ # Feature launcher & subscription UI
├── shizuku/
│   ├── app/                     # Shizuku standalone app
│   ├── core/                    # Shizuku core logic
│   ├── services/                # Shizuku service implementation
│   └── ui/                      # Shizuku UI components
├── android_framework/
│   ├── services/                # Framework services (300+ files)
│   ├── services-29/             # Android 10 specific services
│   ├── services-30/             # Android 11 specific services
│   ├── services-31/             # Android 12 specific services
│   ├── patchx/                  # Xposed hooking framework
│   ├── patchx-29/               # Android 10 patches
│   ├── patchx-30/               # Android 11 patches
│   ├── patchx-31/               # Android 12 patches
│   ├── patchx-entry/            # PatchX entry point
│   ├── db/                      # Database layer
│   ├── utils/                   # Utility classes
│   └── tests/                   # Framework tests
└── third_party/
    └── sec_net/                 # Security/native verification
```

### Module Details

#### 1. Subscription Module (`module_sub`)
- **Package**: `now.fortuitous.app.donate`
- **Purpose**: Premium subscription and activation management
- **Key Components**:
  - `Activation` entity - Stores activation codes with timestamps
  - `ActivationDao` - Database access for activations
  - `ActivationDatabase` - Room database for subscription data
  - `Repo` - Repository pattern for subscription data
  - `DonateSettingsKt` - Donation settings UI

#### 2. Feature Launcher Module (`module_feature_launcher`)
- **Package**: `github.tornaco.android.thanos.app.module.feature`
- **Purpose**: Main subscription UI and feature gating
- **Key Components**:
  - `SubscribeScreen.kt` - Subscription purchase UI (Compose)
  - `LVLCheckScreen.kt` - License verification UI
  - `DeviceCodeBinding.kt` - Device binding for subscriptions
  - `SubscriptionConfig2.kt` - Subscription pricing/flavors
  - `CodeApi.kt` - API for activation code verification
  - `JOtp.kt` - OTP generation for verification
  - `FlameAuroraBackground.kt` - Premium UI effects

**Subscription Features**:
- Multiple pricing tiers (USD and CNY)
- Device code binding (FLAG_CB = 200)
- Email and QQ support contact
- Remaining hours/millis tracking
- Native security verification via `sec_net`

#### 3. Shizuku Module
- **Purpose**: Alternative to root access using Shizuku framework
- **Components**:
  - **app**: `ThanoxLiteApp`, `ShortcutStubActivity`, crash handling
  - **core**: Proto buffers, common interfaces, extensions
  - **services**: `ThanoxService` (962 lines) - Main service implementation
  - **ui**: Compose UI for Shizuku mode

**ThanoxService Capabilities**:
- Process management (kill, force stop)
- Package freezing/suspending (PM Suspend method)
- Activity/component management
- CPU usage tracking
- Memory info and swap info
- Traffic stats monitoring
- Task stack listening
- Logcat logging
- Screen off automation
- Multi-user support

**Key APIs**:
```kotlin
- freezePkgs(pkgs: List<Pkg>, freeze: Boolean)
- forceStopPackage(pkg: Pkg, reason: String)
- getRunningAppProcessLegacy()
- getMemoryInfo()
- getSwapInfo()
- queryProcessCpuUsageStats()
- launchFreezedAppForUser()
- syncSFSettings() // Smart Freeze settings
- syncBCSettings()  // Background Clean settings
```

#### 4. Framework Services (`android_framework/services`)
- **Size**: 300+ source files
- **Purpose**: Deep system integration via framework patches
- **Key Features**:
  - LibSU fallback shell implementation
  - ThanosShellCommand for root commands
  - Process and package management
  - System service hooks

**Version-Specific Modules**:
- `services-29`: Android 10 (Q)
- `services-30`: Android 11 (R)
- `services-31`: Android 12 (S)
- Additional versions up to API 35 in main project

#### 5. PatchX Framework (`android_framework/patchx*`)
- **Size**: 76+ hook registry files
- **Purpose**: Xposed-style hooking framework
- **Hook Categories**:

| Category | Hook Registries |
|----------|----------------|
| Privacy | SecureSettings, PMSGetPackageInfo, TelephonyManager, OpsService, PMSGetInstalledPackages, AdjustWindowBrightness, SettingsEdit |
| Input | InputManagerInjectInput, PWMInterceptKey, InputManagerService |
| Window Manager | WindowProcessController, WMSRegistry, BackNavRegistry |
| Activity | AMSRemoveLruProcess, AMSStartProcessLocked, IntentFirewallRegistry |
| Audio | MediaFocusRegistry |
| System | AppWidgetRegistry, ActivityIntentResolverRegistry, ErrorReporter |

**Version-Specific Patches**:
- `patchx-29`: Android 10
- `patchx-30`: Android 11
- `patchx-31`: Android 12
- `patchx`: Generic/base patches
- `patchx-entry`: Entry point for patch injection

#### 6. Database Module (`android_framework/db`)
- **Purpose**: Local data persistence for rules and records
- **Databases**:
  - `RuleDb` - Automation rules storage
  - `StartDb` - App start records tracking
  - `OpsDb` - Operations history
  - `NRDb` - Notification records

**Entities**:
- `RuleRecord` - Automation rule definitions
- `StartRecord` - App launch history
- `OpRecord` - Operation logs
- `NR` - Notification records

#### 7. Security Module (`third_party/sec_net`)
- **Package**: `tornaco.android.sec.net`
- **Purpose**: Native security verification
- **Implementation**:
  ```java
  public static native void c(String plain, String expect);
  ```
- **Native Library**: `libtn.so` (loaded via `System.loadLibrary("tn")`)
- **Usage**: Subscription validation and anti-tampering

### Build Configuration

**Internal projects** are loaded dynamically in `settings.gradle.kts`:
```kotlin
val internalProjects = listOf(
    ":modules:module_sub",
    ":modules:module_feature_launcher",
    ":third_party:sec_net",
    ":android_framework:tests",
    ":android_framework:db",
    ":android_framework:services",
    ":android_framework:services-28" through ":android_framework:services-35",
    ":android_framework:patchx-entry",
    ":android_framework:patchx",
    ":android_framework:patchx-29" through ":android_framework:patchx-31",
    ":android_framework:utils",
    ":shizuku:core",
    ":shizuku:services",
    ":shizuku:ui",
    ":shizuku:app",
)
```

Project directories mapped to `internal/Thanox-Internal/` subdirectory.

### CI/CD Integration

**GitHub Actions Workflow** (`publish_packages.yml`):
- Automatic package publishing on push
- Encrypted keystore decryption (AES-256-CBC)
- Gradle publish task execution
- Secrets required:
  - `K` and `IV` for keystore decryption
  - `KEYSTORE_PASS` for signing
  - `GPR_USER_NAME` for GitHub Packages
  - `GITHUB_TOKEN` for authentication

### License Verification Flow

1. User purchases subscription via `SubscribeScreen`
2. Activation code stored in `Activation` database
3. Code verified via `CodeApi` with device binding
4. Native validation through `sec_net` library
5. Features unlocked based on subscription status
6. Settings synced via `syncSFSettings`/`syncBCSettings`

### Feature Gating

Premium features controlled by subscription:
- Smart Freeze automation
- Background Clean automation
- Advanced process management
- Extended logging
- Multi-user support
- Screen-off automation

### Security Measures

1. **Native Code Verification**: `sec_net` library for tamper detection
2. **Device Binding**: Subscriptions tied to device ID
3. **String Obfuscation**: StringFog XOR encryption
4. **ProGuard Rules**: Custom obfuscation dictionaries
5. **Random Package Names**: Runtime package name generation
6. **Encrypted Keystore**: AES-256-CBC encrypted signing keys

---

*Internal modules analysis added: 2026-02-28*
