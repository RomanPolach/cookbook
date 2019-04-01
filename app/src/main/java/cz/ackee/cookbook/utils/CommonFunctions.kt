package cz.ackee.cookbook.utils

import cz.ackee.cookbook.BuildConfig

/**
 * Common functions and properties not related to any class
 */

/**
 * Global property indicating if application is debuggable
 */
val isDebug = BuildConfig.DEBUG

val isBeta = BuildConfig.BUILD_TYPE == "beta"