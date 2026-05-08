package com.example.core.model

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for the Result sealed class.
 * Tests all success, error, and loading states.
 */
class ResultTest {

    @Test
    fun `Success result contains data correctly`() {
        val data = "test data"
        val result = Result.Success(data)

        assertTrue(result.isSuccess)
        assertFalse(result.isError)
        assertFalse(result.isLoading)
        assertEquals(data, result.getOrNull())
        assertEquals(data, result.getOrThrow())
    }

    @Test
    fun `Error result contains error message correctly`() {
        val errorMessage = "Something went wrong"
        val exceptionClass = "IllegalStateException"
        val exceptionMessage = "Test exception"

        val result = Result.Error(
            message = errorMessage,
            exceptionClass = exceptionClass,
            exceptionMessage = exceptionMessage
        )

        assertFalse(result.isSuccess)
        assertTrue(result.isError)
        assertFalse(result.isLoading)
        assertNull(result.getOrNull())

        var capturedMessage: String? = null
        var capturedExceptionClass: String? = null
        var capturedExceptionMessage: String? = null

        result.onError { message, exClass, exMessage ->
            capturedMessage = message
            capturedExceptionClass = exClass
            capturedExceptionMessage = exMessage
        }

        assertEquals(errorMessage, capturedMessage)
        assertEquals(exceptionClass, capturedExceptionClass)
        assertEquals(exceptionMessage, capturedExceptionMessage)
    }

    @Test
    fun `Loading result has correct state`() {
        val result = Result.Loading

        assertFalse(result.isSuccess)
        assertFalse(result.isError)
        assertTrue(result.isLoading)
        assertNull(result.getOrNull())
    }

    @Test(expected = IllegalStateException::class)
    fun `getOrThrow throws exception for Error result`() {
        val result = Result.Error("Error message")
        result.getOrThrow()
    }

    @Test(expected = IllegalStateException::class)
    fun `getOrThrow throws exception for Loading result`() {
        val result = Result.Loading
        result.getOrThrow()
    }

    @Test
    fun `map transforms Success data correctly`() {
        val originalData = 42
        val result = Result.Success(originalData)

        val mappedResult = result.map { it * 2 }

        assertTrue(mappedResult is Result.Success)
        assertEquals(84, (mappedResult as Result.Success).data)
    }

    @Test
    fun `map returns Error unchanged`() {
        val errorResult: Result<Int> = Result.Error("Original error")

        val mappedResult = errorResult.map { it * 2 }

        assertTrue(mappedResult is Result.Error)
        assertEquals("Original error", (mappedResult as Result.Error).message)
    }

    @Test
    fun `map returns Loading unchanged`() {
        val loadingResult: Result<Int> = Result.Loading

        val mappedResult = loadingResult.map { it * 2 }

        assertTrue(mappedResult is Result.Loading)
    }

    @Test
    fun `flatMap chains Success results correctly`() {
        val result = Result.Success(10)

        val flatMappedResult = result.flatMap { Result.Success(it * 2) }

        assertTrue(flatMappedResult is Result.Success)
        assertEquals(20, (flatMappedResult as Result.Success).data)
    }

    @Test
    fun `flatMap returns Error when transform fails`() {
        val result = Result.Success(10)

        val flatMappedResult = result.flatMap { Result.Error("Transformed error") }

        assertTrue(flatMappedResult is Result.Error)
        assertEquals("Transformed error", (flatMappedResult as Result.Error).message)
    }

    @Test
    fun `flatMap preserves Error without calling transform`() {
        val errorResult: Result<Int> = Result.Error("Original error")

        var transformCalled = false
        val flatMappedResult = errorResult.flatMap {
            transformCalled = true
            Result.Success(it * 2)
        }

        assertFalse(transformCalled)
        assertTrue(flatMappedResult is Result.Error)
        assertEquals("Original error", (flatMappedResult as Result.Error).message)
    }

    @Test
    fun `onSuccess executes action for Success result`() {
        val result = Result.Success("test")
        var capturedData: String? = null

        result.onSuccess { capturedData = it }

        assertEquals("test", capturedData)
    }

    @Test
    fun `onSuccess does not execute action for Error result`() {
        val result: Result<String> = Result.Error("error")
        var executed = false

        result.onSuccess { executed = true }

        assertFalse(executed)
    }

    @Test
    fun `onSuccess does not execute action for Loading result`() {
        val result: Result<String> = Result.Loading
        var executed = false

        result.onSuccess { executed = true }

        assertFalse(executed)
    }

    @Test
    fun `onError executes action for Error result`() {
        val result: Result<String> = Result.Error("error message", "ExceptionClass", "exception message")
        var capturedMessage: String? = null

        result.onError { message, _, _ -> capturedMessage = message }

        assertEquals("error message", capturedMessage)
    }

    @Test
    fun `onError does not execute action for Success result`() {
        val result = Result.Success("test")
        var executed = false

        result.onError { _, _, _ -> executed = true }

        assertFalse(executed)
    }

    @Test
    fun `Error result with null exceptionClass and exceptionMessage`() {
        val result = Result.Error("Simple error")

        assertEquals("Simple error", result.message)
        assertNull(result.exceptionClass)
        assertNull(result.exceptionMessage)
    }
}