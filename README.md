# VibePlayer

VibePlayer is a modern, modular Android music player built with Kotlin and Jetpack Compose. It scans local audio files, builds a lightweight library, and plays them using ExoPlayer (AndroidX Media3).

This application is developed for "Mobile Dev Campus".

## Features

- Local music scanning with basic constraints (duration and size)
- Library browsing UI with Jetpack Compose
- Player screen with playback controls and progress slider
- ExoPlayer-based playback (Media3)
- Offline-first data layer backed by Room
- Dependency Injection with Koin
- Modular, feature-first architecture

## Project Modules

- app — Application entry point and DI bootstrap
- core/common — Shared primitives (for example, Result and Error)
- core/data — Repositories, mappers, data utilities, DI
- core/database — Room database, DAOs, entities, schemas
- core/designsystem — Compose components, theme, and resources
- core/domain — Domain models, use cases, and player abstractions
- core/player — ExoPlayer implementation of MusicPlayer and DI
- core/ui — UI utilities, mappers, and resources
- feature/library — Library and music scanning feature (UI and logic)
- feature/player — Player feature (UI and logic)
- feature/onboarding — Onboarding feature (UI and logic)
- feature/scanner — Media scanner feature (UI and logic)
- build-logic — Gradle convention plugins

## Tech Stack

- Kotlin, Coroutines, Flows
- Jetpack Compose
- AndroidX Media3 (ExoPlayer)
- Room
- Koin DI
- Gradle Kotlin DSL

## Getting Started

1. Prerequisites
   - Android Studio (latest stable)
   - Android SDK and a device or emulator
2. Open the project in Android Studio and let it sync
3. Run the app configuration on a device or emulator

Command line build: `./gradlew assembleDebug`

## Package

- Application ID: com.fomaxtro.vibeplayer

## Author

- [Fomaxtro](https://github.com/fomaxtro)

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- Built for Mobile Dev Campus
- Thanks to the AndroidX and Jetpack Compose communities