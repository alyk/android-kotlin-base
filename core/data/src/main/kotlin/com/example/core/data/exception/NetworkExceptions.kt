package com.example.core.data.exception

/**
 * Custom exceptions for network operations.
 * Provides detailed error information for handling different failure scenarios.
 */

/**
 * Exception thrown when network connectivity is unavailable
 */
class NoNetworkException(
    message: String = "No internet connection available"
) : Exception(message)

/**
 * Exception thrown when a timeout occurs during network request
 */
class NetworkTimeoutException(
    message: String = "Network request timed out"
) : Exception(message)

/**
 * Exception thrown when the server returns an error response
 */
class ServerException(
    val statusCode: Int,
    override val message: String? = null
) : Exception(message ?: "Server error with status code: $statusCode")

/**
 * Exception thrown when a resource is not found
 */
class NotFoundException(
    override val message: String = "Resource not found"
) : Exception(message)

/**
 * Exception thrown when there's an authentication/authorization failure
 */
class UnauthorizedException(
    override val message: String = "Unauthorized access"
) : Exception(message)

/**
 * Exception thrown when there's a conflict (e.g., duplicate resource)
 */
class ConflictException(
    override val message: String = "Resource conflict"
) : Exception(message)

/**
 * Exception thrown when rate limiting is applied
 */
class RateLimitException(
    val retryAfterSeconds: Int,
    override val message: String = "Rate limit exceeded"
) : Exception(message)

/**
 * Exception thrown for unknown/unexpected errors
 */
class UnknownNetworkException(
    override val message: String = "An unexpected error occurred",
    override val cause: Throwable? = null
) : Exception(message, cause)

/**
 * Extension function to map HTTP status codes to custom exceptions
 */
fun Exception.toNetworkException(): Exception {
    return when (this) {
        is java.net.UnknownHostException -> NoNetworkException()
        is java.net.SocketTimeoutException -> NetworkTimeoutException()
        is java.net.ConnectException -> NoNetworkException()
        else -> this
    }
}