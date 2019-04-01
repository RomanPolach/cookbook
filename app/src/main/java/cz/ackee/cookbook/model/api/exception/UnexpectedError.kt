package cz.ackee.cookbook.model.api.exception

/**
 * Object for unexpected error in API.
 */
class UnexpectedError(cause: Throwable) : RuntimeException("Unexpected error is thrown, crash the system", cause)