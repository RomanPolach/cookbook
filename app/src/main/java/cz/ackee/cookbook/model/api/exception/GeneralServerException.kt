package cz.ackee.cookbook.model.api.exception

/**
 * Exception on server that application does not know how to handle
 */
class GeneralServerException(code: Int) : Exception()