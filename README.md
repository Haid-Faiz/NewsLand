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
