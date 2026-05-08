package com.example.core.database.exception

import com.example.core.model.Result

/**
 * Custom exceptions for database operations.
 * Provides detailed error information for handling different failure scenarios.
 */

/**
 * Exception thrown when a database operation fails
 */
open class DatabaseException(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause)

/**
 * Exception thrown when a record is not found in the database
 */
class NotFoundException(
    override val message: String = "Record not found in database"
) : DatabaseException(message)

/**
 * Exception thrown when there's a constraint violation (e.g., duplicate key)
 */
class ConstraintViolationException(
    override val message: String = "Database constraint violation",
    val constraintName: String? = null
) : DatabaseException(message)

/**
 * Exception thrown when there's a concurrency conflict
 */
class ConcurrencyException(
    override val message: String = "Database concurrency conflict"
) : DatabaseException(message)

/**
 * Exception thrown when migration fails
 */
class MigrationException(
    override val message: String = "Database migration failed",
    val oldVersion: Int,
    val newVersion: Int
) : DatabaseException(message)

/**
 * Exception thrown when the database is in an invalid state
 */
class InvalidDatabaseStateException(
    override val message: String = "Database is in an invalid state"
) : DatabaseException(message)

/**
 * Exception thrown when disk space is insufficient
 */
class InsufficientStorageException(
    override val message: String = "Insufficient storage space for database operation"
) : DatabaseException(message)

/**
 * Exception thrown when there's a transaction failure
 */
class TransactionException(
    override val message: String = "Database transaction failed",
    override val cause: Throwable? = null
) : DatabaseException(message, cause)

/**
 * Extension function to map common exceptions to database exceptions
 */
fun Exception.toDatabaseException(): DatabaseException {
    return when (this) {
        is android.database.sqlite.SQLiteConstraintException -> 
            ConstraintViolationException(message = message ?: "Constraint violation")
        is android.database.sqlite.SQLiteAbortException -> 
            ConcurrencyException(message = message ?: "Concurrency conflict")
        is android.database.sqlite.SQLiteFullException -> 
            InsufficientStorageException(message = message ?: "Storage full")
        is IllegalStateException -> 
            InvalidDatabaseStateException(message = message ?: "Invalid state")
        else -> DatabaseException(message = message ?: "Unknown database error", cause = this)
    }
}

/**
 * Result wrapper for database operations that converts exceptions
 */
inline fun <T> safeDatabaseOperation(block: () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: Exception) {
        Result.Error(e.message ?: "Database operation failed")
    }
}