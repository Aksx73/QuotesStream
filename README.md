[![Surface-Pro-8-2-1.png](https://i.postimg.cc/tTWcR3B0/Surface-Pro-8-2-1.png)](https://postimg.cc/cg1hXnqT)

# Quotes Stream
Quotes application built to demonstrate Pagination with Remote Mediator following MVVM architecture

***Download and test latest version 👇***

[![QuotesStream](https://img.shields.io/badge/QuotesStream-APK-blue.svg?style=for-the-badge&logo=android)]($$apklinkhere$$)

## About

App loads quotes from open source quotations API [Quotable](https://github.com/lukePeavey/quotable).
Data will be always loaded from local database. Remote data (from API) and Local data is always synchronized.
App follows clean architecture pattern (MVVM). Whole app is organized with intent to create architecture like in multi-module approach.

A RemoteMediator implementation helps load paged data from the network into the database, but doesn't load data directly into the UI. Instead, the app uses the database as the [source of truth](https://developer.android.com/jetpack/guide/data-layer#source-of-truth).


## Tech stack 🛠

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android
  development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - A coroutine is a
  concurrency design pattern that you can use on Android to simplify code that executes
  asynchronously.
- [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - A flow is an asynchronous
  version of a Sequence, a type of collection whose values are lazily produced.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
  - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - Paging library helps you load and display pages of data from a larger dataset from local storage or over network.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Figma](https://figma.com/) - Figma is a browser-based UI and UX design application, with excellent design, prototyping, and code options.

# Package Structure

    # Root Package
    .
    ├── data                # For data handling.
    │   ├── local           # Local Persistence Database. Room (SQLite) database
    |   │   ├── dao         # Data Access Object for Room   
    |   |   |── database    # Datbase Instance
    |   |── remote          # Remote Data Handlers
    │   |   ├── api         # Retrofit API for remote end point
    |   |── model           # Model classes
    |   └── repository      # Repository implementation
    |
    ├── domain              # Model classes
    |   |── usecase         # Usescase classes
    |   └── repository      # Repository interface (Single source of data)
    |
    ├── di                  # Dependency injection modules 
    |
    ├── ui                  # Presentation layer
    │   |── application     # Application class
    |   ├── activity        # Main activity
    |   |── home            # Home fragment  
    |   ├── viewmodel       # Common viewmodel for all data
    |   ├── spacecraft      # Fragment + adapter for spacecraft list
    |   ├── centres         # Fragment + adapter for isro centres list
    |   ├── launchers       # Fragment + adapter for isro launchers list
    |   └── satellite       # Fragment + adapter for customer satellite list
    |
    └── utils               # Utility classes

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture and uses [***RemoteMediator***](https://developer.android.com/reference/kotlin/androidx/paging/RemoteMediator) to get page from network and database.

[![mvvm-pagingination-remotemediator.jpg](https://i.postimg.cc/X7n4BqSS/mvvm-pagingination-remotemediator.jpg)](https://postimg.cc/CdrWWFxm)

## MAD Score

![Summary](https://raw.githubusercontent.com/Aksx73/ISRO-Archive/master/media/MAD_summary.png?token=GHSAT0AAAAAABSKJIP7ACNDTEEJPI7JBHMCYXJCJKQ)

