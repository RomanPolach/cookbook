package cz.ackee.cookbook.model.validation

/**
 * Alias for a map with validation errors as values
 */
typealias ValidationErrorMap = Map<String, ValidationError>

/**
 * Validation exception containing map of validation errors. Can be passed to State.Error
 */
class ValidationException(val error: ValidationErrorType) : Exception() {

    enum class ValidationErrorType {
        EMPTY_FIELD
    }
}