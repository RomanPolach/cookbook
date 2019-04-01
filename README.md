# Ackee Android project skeleton 

## Project
This is Ackee skeleton, but you may want to write some general info about project evolving from this skeleton.

## Project setup

1. Don't forget to remove the `.git` dir and init a new repo.
2. Don't forget to change `packageName` from `cz.ackee.skeleton` to something more related to project.
3. Follow the steps at: [our glorious wiki](https://wiki.ack.ee/doku.php?id=guidelines:android:jakzalozitprojekt).
4. Crashlytics is now a mandatory thing, create a Firebase app and add `google.json` config file.
5. Change the uppermost README tag to project name, remove `Project setup` tag and its contents.
6. Write some general info about the project under the `Project` tag.

## Tech stack

The app is written in Kotlin language using the latest Ackee's tech stack in early-2019.
It is implementing the MVVM architecture using the Android architecture components ViewModel and highly utilizes the Repository design pattern.
Koin DI framework is used to manage component dependencies. Lists UI presentation is handled using Epoxy library.
All the remote data are downloaded using the famous Retrofit library, stored locally in SQLite database and are observed using the Google's Room library and the RxJava2.

* RxJava2
* Koin
* Epoxy
* OkHttp & Retrofit
* Architecture components
* Anko Layouts & Coroutines
* Timber
* ThreeTen
* Picasso
* Various Ackee home-made extensions

## Build and CI

The Gradle wrapper is the best option to build the project.

There are 3 build types and 2 flavors in the project.

The product flavors specify to which api the app connects.

| **Flavor** | **Description** |
|:----------:|:----------------|
| devApi     | properties to use develepment API |
| prodApi    | properties to use production API  |

Build types define the debuggability of the project and specify the signing certificate and the level of ProGuard/R8 optimizations.

| **Build type** | **Description** |
|:--------------:|----------------------------------|
| debug          | Debug builds are used by developer only to verify app functionality |
| beta           | Beta builds are sent to HockeyApp for internal Ackee testing |
| release        | Release builds are those sent to client (dedicated HockeyApp) and Play Store |


To assemble the production-ready app use the following command in the root directory of the project:

    ./gradlew assembleProdApiRelease

The output apk and mapping are copied to the directory `outputs`

The project contains the Jenkinsfile to configure the CI with the necessary values for the Ackee build pipeline. After the successful build the new version of app is being uploaded to HockeyApp continuous delivery service.

## Crash reporting

Crash reports are collected using the HockeyApp SDK and Firebase Crashlytics.
