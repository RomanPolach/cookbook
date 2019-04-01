package cz.ackee.skeleton.utils

import cz.ackee.skeleton.BuildConfig

/**
 * Common functions and properties not related to any class
 */

/**
 * Global property indicating if application is debuggable
 */
val isDebug = BuildConfig.DEBUG

val isBeta = BuildConfig.BUILD_TYPE == "beta"