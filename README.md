# Molla

![Icon](icon.png)

Molla is an alternative launcher for Android, primarily designed for handheld gaming devices.

## Download

[<img src="images/googleplay.png">](https://play.google.com/store/apps/details?id=com.sinu.molla)

or you can grab the APK from [here](https://github.com/sinusinu/Molla/releases/latest).

## Features

- D-pad first design - no touchscreen required
- A simple and intuitive Android TV-esque interface
- Packed with a bunch of ~~weird~~ niche features, including:
  - Separate wallpaper from system
  - Make Molla closeable
  - Autostart Molla at boot automatically
  - Launch an app at boot automatically
  - Custom shortcuts with "advanced options"
  - and more!

## Screenshots

![Main Screen with Quick Access](images/1.png)

![All Apps](images/2.png)

![Editing Quick Access](images/3.png)

Wallpaper is [Mountain dew during sunrise](https://unsplash.com/photos/mountain-dew-during-sunrise-xJ2tjuUHD9M) by Paul Earle.

## Building from Source

### Prerequisites
- JDK 11 or higher
- Android SDK (automatically downloaded by Gradle)

### Build Instructions

#### Using Gradle Wrapper (Recommended)
```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease
```

The built APKs will be available in:
- `app/build/outputs/apk/debug/` - Debug APKs (signed)
- `app/build/outputs/apk/release/` - Release APKs (signed)

**Note:** All builds are signed with a consistent key to allow seamless updates:
- **Local development**: Uses the debug keystore (`~/.android/debug.keystore`)
- **GitHub Actions**: Uses a custom release keystore if configured via secrets, otherwise falls back to debug keystore

#### Configuring Release Signing for GitHub Actions

To use a custom signing key for releases built on GitHub Actions:

1. **Generate a keystore** (if you don't have one):
   ```bash
   keytool -genkey -v -keystore release.keystore -alias release -keyalg RSA -keysize 2048 -validity 10000
   ```

2. **Encode the keystore to base64**:
   ```bash
   base64 -w 0 release.keystore > release.keystore.base64
   ```

3. **Add GitHub Secrets** to your repository (Settings → Secrets and variables → Actions):
   - `KEYSTORE_BASE64`: The base64-encoded keystore file content
   - `KEYSTORE_PASSWORD`: Your keystore password
   - `KEY_ALIAS`: Your key alias (e.g., "release")
   - `KEY_PASSWORD`: Your key password

Once configured, all builds (both debug and release) will be signed with the same key, allowing seamless updates without signature mismatches.

#### Multi-ABI Support
The project is configured to build separate APKs for different CPU architectures:
- **armeabi-v7a** - 32-bit ARM devices
- **arm64-v8a** - 64-bit ARM devices
- **universal** - APK that works on all architectures

### GitHub Actions
The project includes automated builds via GitHub Actions. On every push or pull request to the main branch, the workflow automatically builds both debug and release APKs for all supported architectures. Built APKs are available as artifacts in the Actions tab.

## License

Molla is distributed under the GNU GPL v3.
