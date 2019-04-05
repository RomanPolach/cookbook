package cz.ackee.cookbook.model.validation

/**
 * Validation exception, can be passed to State.Error
 */
class ValidationException(val error: ValidationErrorType) : Exception() {

    enum class ValidationErrorType {
        EMPTY_FIELD
    }
}