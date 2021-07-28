## NewsLand
[![Build](https://github.com/Haid-Faiz/NewsLand/actions/workflows/android_build_ci.yml/badge.svg)](https://github.com/Haid-Faiz/NewsLand/actions/workflows/android_build_ci.yml)
[![Kotlin Lint](https://github.com/Haid-Faiz/NewsLand/actions/workflows/kotlin_lint_ci.yml/badge.svg)](https://github.com/Haid-Faiz/NewsLand/actions/workflows/kotlin_lint_ci.yml)
[![Unit Tests](https://github.com/Haid-Faiz/NewsLand/actions/workflows/unit_tests_ci.yml/badge.svg)](https://github.com/Haid-Faiz/NewsLand/actions/workflows/unit_tests_ci.yml)
[![Platform](https://img.shields.io/badge/platform-android-blue.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-23%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=23)

**It shows news under following catagories:**
- Top Headlines wise news from 5 country
- Category wise news from 5 category
- Source wise news from 5 sources

### Architecture
The project follows the MVVM structure with Modular Architecture without any specifics. 

There are two _modules_ in the project 

* `app` - The UI of the app. The main project that forms the APK
* `datastore` - The REST API consumption and local database android library

###  The following libraries were used in this project.

* MVVM (Model View ViewModel) Architecture
* Flow & LiveData
* Kotlin Coroutines
* Dagger-Hilt for Dependency Injection
* Pagination using Jetpack Paging 3
* Jetpack Navigation Architecture
* Retrofit
* Room Database
* Moshi
* Coil
* Chrome Custom Tab
* Preferences DataStore
* Lottie Animation


- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.
- [Coil-kt](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
