## NewsLand
[![Build](https://github.com/Haid-Faiz/NewsLand/actions/workflows/android_build_ci.yml/badge.svg)](https://github.com/Haid-Faiz/NewsLand/actions/workflows/android_build_ci.yml)
[![Kotlin Lint](https://github.com/Haid-Faiz/NewsLand/actions/workflows/kotlin_lint_ci.yml/badge.svg)](https://github.com/Haid-Faiz/NewsLand/actions/workflows/kotlin_lint_ci.yml)
[![Unit Tests](https://github.com/Haid-Faiz/NewsLand/actions/workflows/unit_tests_ci.yml/badge.svg)](https://github.com/Haid-Faiz/NewsLand/actions/workflows/unit_tests_ci.yml)
[![Platform](https://img.shields.io/badge/platform-android-blue.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-23%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=23)

<img src = "https://user-images.githubusercontent.com/56159740/138607350-29e7498e-e6e2-4b54-b18c-2a35576cc080.png" height = "200" width="500">


***It shows news under following catagories:***
- Top Headlines wise news from 5 different country
- Category wise news from 5 different category
- Source wise news from 5 different sources

## ScreenShots
<table>
   <ul>
      <li>
         <h4>App Intro & Search feature<h4>
      </li>
   </ul>
   <tr>
<td><img src = "https://user-images.githubusercontent.com/56159740/138334041-6073b1eb-dae4-4e6a-ad18-c9b86427e417.gif" height = "370" width="200"></td>
<td><img src = "https://user-images.githubusercontent.com/56159740/138608774-1d28f696-aa27-4ee0-a65c-6cfda19c38d2.gif" height = "370" width="200"></td>
<td><img src = "https://user-images.githubusercontent.com/56159740/138395751-3992a126-c522-49f0-9d9f-12e127aa7bfb.gif" height = "370" width="200"></td>
  </tr>
</table>

<table>
      <ul>
      <li>
         <h4>Screen orientation changes & Pagination, etc<h4>
          </li>
   </ul>
  <tr>
<td><img src = "https://user-images.githubusercontent.com/56159740/138395197-8ef241b2-53e6-4abc-b2c9-a7f4c0529cac.gif" height = "370" width="200"></td>
<td><img src = "https://user-images.githubusercontent.com/56159740/138334650-0e63e931-efd1-4ae0-8da4-f9caf7e1691f.gif" height = "370" width="200"></td>
  </tr>
</table>

## Architecture
The project follows the MVVM structure with Modular Architecture without any specifics.

There are two _modules_ in the project

* `app` - The UI of the app. The main project that forms the APK
* `datastore` - The REST API consumption and local database android library

###  The following libraries were used in this project.

- [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Room Database](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Dagger-Hilt](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Jetpack Navigation](https://developer.android.com/guide/navigation) - Android Jetpack's Navigation component helps you implement navigation, from simple button clicks to more complex patterns.
- [Jetpack Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - The Paging library helps you load and display pages of data from a larger dataset from local storage or over network.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.
- [Coil-kt](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines.
- [Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Jetpack DataStore is a data storage solution that allows you to
 store key-value pairs. Basically it's a replacement for SharedPreferences.
- [Chrome Custom Tabs](https://developer.chrome.com/docs/android/custom-tabs/overview/) - Custom Tabs is a browser feature, introduced by Chrome. We can use it like WebView.
- [Lottie Animation](https://airbnb.io/lottie/#/android) - Lottie is a library for that parses animations exported as json and renders them natively.