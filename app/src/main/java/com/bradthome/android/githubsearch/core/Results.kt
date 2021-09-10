package com.bradthome.android.githubsearch.core


import retrofit2.Response


/**
 * ResultState has three states - Success,Error, and Empty. Useful for instances where a success or error has not been
 * returned yet, so empty can be used.
 */
sealed class ResultState<T> {

    data class Empty<T>(val reason: Any? = null) : ResultState<T>()

    val successValue: T?
        get() = (this as? GitResult.Success<T>)?.value

    inline fun <R> onResult(
        error: GitResult.Error<T>.() -> R? = { null },
        empty: Empty<T>.() -> R? = { null },
        success: GitResult.Success<T>.() -> R? = { null },
    ): R? {
        return when (this) {
            is Empty -> this.empty()
            is GitResult.Success -> this.success()
            is GitResult.Error -> this.error()
        }
    }
}


/**
 * Since Kotlin doesnt allow you to use their own Result class, here we have a copy of it
 */
sealed class GitResult<T>(val isSuccess: Boolean) : ResultState<T>() {

    data class Success<T>(val value: T) : GitResult<T>(isSuccess = true)
    class Error<T>(val exception: Exception = Exception("Failed")) : GitResult<T>(isSuccess = false)

    inline fun <R> onResult(
        error: Error<T>.() -> R? = { null },
        success: Success<T>.() -> R? = { null },
    ): R? {
        return when (this) {
            is Success -> this.success()
            is Error -> this.error()
        }
    }

    object BRResultScope {

        fun <T> Exception.asError() = GitResult.Error<T>(this)

        fun <T> Response<T>.onlySuccessful(): Response<T> {
            if (!isSuccessful) throw Exception("Invalid code ${code()}")
            return this;
        }

        fun <T> Response<T>.noEmptyBody(onlyOnSuccess: Boolean = true): T {
            if (!isSuccessful && onlyOnSuccess) throw Exception("Invalid code ${code()}")
            return body() ?: throw Exception("Empty Body")
        }
    }

    companion object {
        fun <T> tryCatch(
            onCatch: BRResultScope.(Exception) -> GitResult<T> = { Error(it) },
            tryResult: BRResultScope.() -> T,
        ) =
            BRResultScope.run {
                try {
                    Success(tryResult())
                } catch (e: Exception) {
                    onCatch(e)
                }
            }


        suspend fun <T> tryCatchSuspend(
            onCatch: suspend BRResultScope.(Exception) -> GitResult<T> = { Error(it) },
            tryResult: suspend BRResultScope. () -> T,
        ) =
            BRResultScope.run {
                try {
                    Success(tryResult())
                } catch (e: Exception) {
                    onCatch(e)
                }
            }
    }
}