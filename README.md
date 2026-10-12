# 🎵 Shake2Play

![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue?logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-UI-green?logo=android)
![Navigation](https://img.shields.io/badge/Navigation-3-orange)
![Tests](https://img.shields.io/badge/Tests-Unit_&_Instrumentation-yellow)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

Welcome to **Shake2Play** — a Kotlin Multiplatform app for Android and iOS that lets you **play a set of sounds by shaking your phone**. Both apps share their interface using Compose Multiplatform, with native integrations kept at each platform boundary.

---

## 📱 About the Project
- Trigger sound playback by **shaking the device**.
- Share the application UI across Android and iOS with **Kotlin Multiplatform** and **Compose Multiplatform**.
- Implement **Navigation 3** for screen transitions.
- Ensure quality with **Unit Tests** and **Instrumentation Tests**.

---

## 🎯 Goals
- Demonstrate motion-based interaction in Android apps.
- Build a clean, scalable architecture using **Kotlin** and **Compose**.
- Provide a reference project for **Navigation3** and **MCP** integration.
- Encourage developers to experiment with **gesture-based triggers**.

---

## 🛠️ Tech Stack
- **Kotlin**  
- **Compose Multiplatform**
- **Kotlin Multiplatform**
- **Navigation 3**  
- **MCP (Multiplatform Compose Project)**  
- **Unit & Instrumentation Tests**  

---

## Project Structure
Shared screens, navigation, and feature logic belong in the common source set of the Compose application module. Android and iOS source sets provide native entry points and platform integrations; both hosts launch the shared Compose UI. See [docs/architecture.md](docs/architecture.md) for the recommended source layout and ownership rules. The checked-in Gradle configuration defines the actual modules and targets.

---

## 🚀 Getting Started
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/Shake2Play.git
   ```
2. Open the project in Android Studio or the configured IDE.
3. Run the Android or iOS app using the target and host setup available in the project.
4. Shake your phone to play sounds!

## 📸 Screenshots
(Coming soon: UI previews and demo of shake-to-play interaction)

## 🤝 Contributing
Contributions, ideas, and suggestions are welcome!
Feel free to open issues or submit pull requests.

## 📢 Disclaimer
This project is for educational purposes only.
All sound assets used are for demonstration and testing.

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

