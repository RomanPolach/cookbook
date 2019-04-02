import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension
import java.util.Properties

plugins {
    id("cz.ackee.build")
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.github.gfx.ribbonizer")
    id("io.fabric")
}

apply(from = "ribbonizer.gradle")

android {
    val gitCommitsCount: Int by extra
    val appProperties: Properties by extra

    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(28)
        applicationId = appProperties["package_name"] as String
        versionName = appProperties["version_name"] as String
        versionCode = gitCommitsCount
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

        addManifestPlaceholders(mapOf(
            "appName" to "Ackee skeleton", // TODO change to actual app name to display for debug and beta builds
            "appNameSuffix" to ""
        ))

        javaCompileOptions {
            annotationProcessorOptions {
                includeCompileClasspath = true
            }
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    productFlavors {
        flavorDimensions("api")

        create("devApi") {
            setDimension("api")
        }
        create("prodApi") {
            setDimension("api")
        }
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/NOTICE")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
        exclude("AndroidManifest.xml")
    }

    if (project.hasProperty("devBuild")) {
        splits.abi.isEnable = false
        splits.density.isEnable = false
        aaptOptions.cruncherEnabled = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

// Used to enable Parcelize
androidExtensions {
    // https://github.com/gradle/kotlin-dsl/issues/644#issuecomment-398502551
    configure(delegateClosureOf<AndroidExtensionsExtension> {
        isExperimental = true
    })
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))

    // AndroidX
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.material:material:1.1.0-alpha03")

    implementation("androidx.lifecycle:lifecycle-runtime:2.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    kapt("androidx.lifecycle:lifecycle-compiler:2.0.0")
    implementation("androidx.room:room-runtime:2.0.0")
    implementation("androidx.room:room-rxjava2:2.0.0")
    kapt("androidx.room:room-compiler:2.0.0")

    implementation("cz.ackee:anko-constraint-layout:0.6.7")
    implementation("com.android.support.constraint:constraint-layout:1.1.0-beta5")

    // Firebase
    implementation("com.google.firebase:firebase-core:16.0.7")

    // Koin
    implementation("org.koin:koin-android:1.0.2")
    implementation("org.koin:koin-android-scope:1.0.2")
    implementation("org.koin:koin-android-viewmodel:1.0.2")

    // Epoxy
    implementation("com.airbnb.android:epoxy:3.0.0")
    kapt("com.airbnb.android:epoxy-processor:3.0.0")

    // Rx
    implementation("io.reactivex.rxjava2:rxjava:2.2.6")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("io.reactivex.rxjava2:rxkotlin:2.3.0")
    implementation("com.jakewharton.rxbinding2:rxbinding:2.2.0")
    implementation("com.jakewharton.rxbinding2:rxbinding-kotlin:2.2.0")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:3.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.12.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.5.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.5.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")


    // Stetho
    implementation("com.facebook.stetho:stetho:1.5.0")
    implementation("com.facebook.stetho:stetho-okhttp3:1.5.0")

    // Anko
    implementation("org.jetbrains.anko:anko-sdk15:0.10.8")
    implementation("org.jetbrains.anko:anko-sdk21-coroutines:0.10.8")
    implementation("org.jetbrains.anko:anko-appcompat-v7:0.10.8")
    implementation("org.jetbrains.anko:anko-design:0.10.8")
    implementation("org.jetbrains.anko:anko-recyclerview-v7:0.10.8")

    // Hockey
    implementation("net.hockeyapp.android:HockeySDK:5.1.1")

    // Crashlytics
    implementation("com.crashlytics.sdk.android:crashlytics:2.9.9")

    // Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    // ThreeTen
    implementation("com.jakewharton.threetenabp:threetenabp:1.1.1")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    // Ackee libraries
    implementation("cz.ackee.useragent:useragent:1.0.4")
    implementation("cz.ackee.extensions:anko:1.1.0")
    implementation("cz.ackee.extensions:android:1.1.0")
    implementation("cz.ackee.extensions:recyclerview:1.1.0")
    implementation("cz.ackee.extensions:epoxy:1.1.0")
    implementation("cz.ackee.extensions:rxjava2:1.1.0")
    implementation("cz.ackee.extensions:picasso:1.1.0")
    implementation("cz.ackee.extensions:viewmodel:1.1.0")

    // Test
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.23.0")
    testImplementation("org.robolectric:robolectric:3.8")

    // Android test
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.1") {
        exclude(module = "support-annotations")
        exclude(group = "com.google.code.findbugs")
    }
}

apply(plugin = "com.google.gms.google-services")
