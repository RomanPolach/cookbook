package cz.ackee.skeleton.model.validation

/**
 * Alias for a map with validation errors as values
 */
typealias ValidationErrorMap = Map<String, ValidationError>

/**
 * Validation exception containing map of validation errors. Can be passed to State.Error
 */
class ValidationException(val errors: ValidationErrorMap) : Exception()