package com.example.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.model.Result

/**
 * A wrapper component that handles different UI states from a Result.
 * Displays loading, error, or content based on the Result state.
 */
@Composable
fun <T> StateWrapper(
    result: Result<T>,
    onSuccess: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    onLoading: @Composable () -> Unit = {
        LoadingScreen(modifier = modifier)
    },
    onError: @Composable (String) -> Unit = { message ->
        ErrorScreen(
            message = message,
            modifier = modifier
        )
    }
) {
    when (result) {
        is Result.Loading -> onLoading()
        is Result.Success -> onSuccess(result.data)
        is Result.Error -> onError(result.message)
    }
}

/**
 * A wrapper for handling nullable data with optional loading state.
 */
@Composable
fun <T> DataOrEmpty(
    data: T?,
    onSuccess: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable () -> Unit = {
        EmptyListScreen(
            title = "No Data",
            message = "There's no data available.",
            modifier = modifier
        )
    },
    isLoading: Boolean = false,
    loadingContent: @Composable () -> Unit = {
        LoadingScreen(modifier = modifier)
    }
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            isLoading -> loadingContent()
            data == null -> emptyContent()
            else -> onSuccess(data)
        }
    }
}

/**
 * A wrapper that shows loading initially, then content or error.
 */
@Composable
fun <T> LoadingContent(
    isLoading: Boolean,
    data: T?,
    error: String?,
    onSuccess: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    onLoading: @Composable () -> Unit = {
        LoadingScreen(modifier = modifier)
    },
    onError: @Composable (String) -> Unit = { message ->
        ErrorScreen(
            message = message,
            modifier = modifier
        )
    },
    emptyContent: @Composable (() -> Unit)? = null
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            isLoading -> onLoading()
            error != null -> onError(error)
            data != null -> onSuccess(data)
            emptyContent != null -> emptyContent()
            else -> onError("Unknown state")
        }
    }
}

/**
 * Extension to convert Result to a boolean for easier conditional rendering
 */
val <T> Result<T>.isLoading: Boolean
    get() = this is Result.Loading

val <T> Result<T>.isSuccess: Boolean
    get() = this is Result.Success

val <T> Result<T>.isError: Boolean
    get() = this is Result.Error

/**
 * Safe access to data with null fallback
 */
fun <T> Result<T>.getOrNull(): T? {
    return when (this) {
        is Result.Success -> data
        else -> null
    }
}

/**
 * Safe access to error message
 */
fun <T> Result<T>.errorOrNull(): String? {
    return when (this) {
        is Result.Error -> message
        else -> null
    }
}