# 🎵 Shake2Play

![License](https://img.shields.io/badge/License-MIT-lightgrey)

**Shake2Play** is a Kotlin Multiplatform starter for Android and iOS, with a shared Compose Multiplatform UI. The generated starter screen is in place; motion-triggered sound playback is planned, not implemented yet.

---

## 📱 About the Project
- **Configured:** Android and iOS Kotlin Multiplatform targets with a shared Compose UI.
- **Planned:** motion-triggered sound playback and the platform integrations it requires.
- **Tested:** shared soundboard empty and populated state using `kotlin.test` in `commonTest`.

---

## 🎯 Goals
- Build a clean, scalable Android and iOS application using Kotlin Multiplatform and Compose Multiplatform.
- Add motion-based sound playback in a later feature iteration.

---

## 🛠️ Configured Stack
- Kotlin Multiplatform
- Compose Multiplatform
- Gradle version catalog
- `kotlin.test` for shared tests

---

## Project Structure
Shared UI and platform-independent logic belong in `shared/src/commonMain`. Android-specific code belongs in `shared/src/androidMain` and the Android host is in `androidApp`; iOS-specific shared code belongs in `shared/src/iosMain` and the SwiftUI host is in `iosApp`. See [docs/architecture.md](docs/architecture.md) for ownership rules and [docs/project-map.md](docs/project-map.md) for the current modules.

---

## 🚀 Getting Started
1. Open the repository in Android Studio or another IDE with Gradle support.
2. Configure an Android SDK using `ANDROID_HOME` or a local `sdk.dir` entry in `local.properties` (that file is ignored by Git).
3. From the repository root, build the Android debug app:
   ```bash
   ./gradlew :androidApp:assembleDebug
   ```
4. Run the app on an Android emulator or device from Android Studio. Motion-triggered playback is not implemented yet.

For current test commands and host limitations, see [docs/build-and-test.md](docs/build-and-test.md).

### Run from a terminal

The following example is for macOS or Linux. Adjust `ANDROID_HOME` if your SDK is installed elsewhere:

```bash
export ANDROID_HOME="$HOME/Library/Android/sdk"
export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$PATH"

# In one terminal, list the available emulators and start one.
emulator -list-avds
emulator -avd <AVD_NAME>
```

With the emulator running (or an Android device connected with USB debugging enabled), open a second terminal at the repository root and run:

```bash
adb devices
./gradlew :androidApp:installDebug
adb shell am start -n com.saishaddai/.MainActivity
```

`adb devices` should list the target device before installing. The app opens on the shared Shake2Play home screen; motion-triggered playback is not implemented yet.

If installation fails with `Requested internal only, but not enough space`, free internal storage on the device or emulator and retry `./gradlew :androidApp:installDebug`.

## 📸 Screenshots
(Coming soon: UI previews and demo of shake-to-play interaction)

## 🤝 Contributing
Contributions, ideas, and suggestions are welcome!
Feel free to open issues or submit pull requests.

## ⭐ Support
If you find this project helpful or inspiring:
- Give it a ⭐ on GitHub
- Share it with other developers exploring gesture-based Android apps

📚 Resources
- Jetpack Compose Documentation
- Navigation Component
- Kotlin Multiplatform (kotlinlang.org in Bing)
- Android Testing


---

