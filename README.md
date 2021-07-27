[![Build](https://github.com/Haid-Faiz/NewsLand/actions/workflows/newsland_ci_pipeline.yml/badge.svg)](https://github.com/Haid-Faiz/NewsLand/actions/workflows/newsland_ci_pipeline.yml)
[![Platform](https://img.shields.io/badge/platform-android-blue.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-23%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=23)

## NewsLand

**It shows news under following catagories:**
- Top Headlines wise news under 5 country
- Category wise news under 5 category
- Source wise news under 5 sources

### Architecture
The project follows the general MVVM structure without any specifics. 

There are two _modules_ in the project 

* `app` - The UI of the app. The main project that forms the APK
* `api` - The REST API consumption library. Pure JVM library not Android-specific

###  The following libraries were used in this project.

* MVVM (Model View ViewModel) Architecture
* LiveData
* Kotlin Coroutines
* Jetpack Navigation Architecture
* Retrofit
* Moshi
* Room Database
* Coil
* Chrome Custom Tab
* Preferences DataStore
