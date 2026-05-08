package com.example.core.model

import kotlinx.serialization.Serializable

/**
 * A generic wrapper class for handling API responses and operations.
 * Follows clean architecture principles by separating success/error states.
 */
@Serializable
sealed class Result<out T> {
    /**
     * Represents a successful operation with data
     */
    @Serializable
    data class Success<T>(val data: T) : Result<T>()

    /**
     * Represents a failed operation with an error message
     */
    @Serializable
    data class Error(
        val message: String,
        val exceptionClass: String? = null,
        val exceptionMessage: String? = null
    ) : Result<Nothing>()

    /**
     * Represents a loading state
     */
    @Serializable
    data object Loading : Result<Nothing>()

    /**
     * Returns true if this is a Success result
     */
    val isSuccess: Boolean get() = this is Success

    /**
     * Returns true if this is an Error result
     */
    val isError: Boolean get() = this is Error

    /**
     * Returns true if this is a Loading result
     */
    val isLoading: Boolean get() = this is Loading

    /**
     * Returns the data if this is a Success, null otherwise
     */
    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    /**
     * Returns the data if this is a Success, throws exception if Error, returns null if Loading
     */
    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw IllegalStateException(message)
        is Loading -> throw IllegalStateException("Result is still loading")
    }

    /**
     * Maps the success value to another type
     */
    inline fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
        is Loading -> this
    }

    /**
     * Flat maps the success value
     */
    inline fun <R> flatMap(transform: (T) -> Result<R>): Result<R> = when (this) {
        is Success -> transform(data)
        is Error -> this
        is Loading -> this
    }

    /**
     * Executes the given block if this is a Success
     */
    inline fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }

    /**
     * Executes the given block if this is an Error
     */
    inline fun onError(action: (String, String?, String?) -> Unit): Result<T> {
        if (this is Error) action(message, exceptionClass, exceptionMessage)
        return this
    }
}