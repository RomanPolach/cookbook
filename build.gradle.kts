buildscript {
    val kotlinVersion by extra("1.3.21")

    repositories {
        google()
        jcenter()
        mavenCentral()
        maven("https://maven.fabric.io/public")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.github.gfx.ribbonizer:ribbonizer-plugin:2.1.0")
        classpath("com.google.gms:google-services:4.2.0")
        classpath("io.fabric.tools:gradle:1.27.1")
        classpath("cz.ackee:build-gradle-plugin:1.0.0-RC13")
    }
}

repositories {
    google()
    jcenter()
    mavenCentral()
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

val clean = task<Delete>("clean") {
    delete(rootProject.buildDir)
}
