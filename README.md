# BiathlonK

## Requirements

For Development, you require at least [Android Studio Narwhal 3 Feature Drop | 2025.1.3](https://developer.android.com/studio).

## Development

* Entirely written in [Kotlin](https://kotlinlang.org/).
* UI completely written in [Jetpack Compose](https://developer.android.com/jetpack/compose).
* Follows the [Guide to app architecture](https://developer.android.com/jetpack/guide).
* Uses [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html) throughout.
* Uses [Hilt](https://dagger.dev/hilt) for dependency injection.
* Uses [Coil](https://coil-kt.github.io/coil/compose/) for image loading.

## Modularization

The app contains the following modules:

| Name                 | Responsibility                                                                                                                                          |
|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| `:app`               | Brings everything together required for the app to function correctly. This includes UI scaffolding and navigation.                                     |
| `:core:common`       | Provides common classes shared between modules, dispatchers and an external scope to offload work to the background.                                    |
| `:core:data`         | Provides app data.                                                                                                                                      |
| `:core:domain`       | Provides use cases.                                                                                                                                     |
| `:core:designsystem` | Defines the design system for the app with theming, colors, typography and more.                                                                        |
| `:core:model`        | Provides model classes used throughout the app.                                                                                                         |
| `:core:network`      | Makes network requests and handles responses from remote data sources.                                                                                  |
| `:core:testing`      | Provides utilities for testing.                                                                                                                         |
| `:core:ui`           | Provides UI components and resources, such as icons and text, used by different features.                                                               |
| `:feature:*`         | Functionality associated with a specific feature or user journeys. Typically contains UI components and ViewModels which reads data from other modules. |
