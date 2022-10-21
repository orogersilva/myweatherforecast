# My Weather Forecast

[![My Weather Forecast Android](https://github.com/orogersilva/myweatherforecast/actions/workflows/ci.yml/badge.svg)](https://github.com/orogersilva/myweatherforecast/actions/workflows/ci.yml)

[![My Weather Forecast App](https://cdn.loom.com/sessions/thumbnails/26daaf99e5c64ee5aef66c5cd7d1f93b-with-play.gif)](https://www.loom.com/share/26daaf99e5c64ee5aef66c5cd7d1f93b "My Weather Forecast App")

**My Weather Forecast** is a work-progress weather forecast Android app.

This project is a personal sandbox app, experimenting with the latest libraries and tools.

*NOTE*: UI and UX is not a priority by now.

## Stack and Libraries

- [Kotlin](https://kotlinlang.org/)
- [Android Compose](https://developer.android.com/jetpack/compose)
- [Accompanist](https://google.github.io/accompanist/)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Chucker](https://github.com/ChuckerTeam/chucker)
- [LeakCanary](https://github.com/square/leakcanary)
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Retrofit](https://github.com/square/retrofit)
- [Gradle Version Catalogs](https://docs.gradle.org/current/userguide/platforms.html)

## Modularization

![](gradle/dependency-graph/project.dot.png)

## Architecture

It was used an approach of [MVVM](https://developer.android.com/topic/architecture) 
(Model-View-ViewModel) targeting Android Compose for the architecture.

## Testing Strategy

It follows the approach proposed by [Kent Dodds](https://kentcdodds.com/blog/write-tests):

> Write tests. Not too many. Mostly integration

- [Fakes over mocks](https://blog.pragmatists.com/test-doubles-fakes-mocks-and-stubs-1a7491dfa3da)
- Unit tests
- Integration tests
- UI tests (coming soon)
- Screenshot tests

## CI

- [Kotlinter Gradle](https://github.com/jeremymailen/kotlinter-gradle)
- [Detekt](https://github.com/detekt/detekt)
- [Android Lint](https://developer.android.com/studio/write/lint)
- [Unit tests](https://developer.android.com/training/testing/local-tests)
- [Integration tests](https://developer.android.com/training/testing/fundamentals)
- [Screenshot tests](https://github.com/cashapp/paparazzi)

## Setup

Download the latest (stable) version of Android Studio.

Clone the project, and build (no API keys or other setup necessary)!

## Author

Roger Silva (follow me on [Twitter](https://twitter.com/orogersilva))

License
=======

    Copyright 2022 Roger Silva.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.