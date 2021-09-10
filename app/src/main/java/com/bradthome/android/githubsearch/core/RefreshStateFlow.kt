package com.bradthome.android.githubsearch.core

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

/**
 * A StateFlow that allows for refreshing the value of the state.  The logic is retained privately in the stateflow, so
 * no need for a mutable state flow externally.
 */
interface RefreshStateFlow<T> : StateFlow<T> {
    val lastRefreshTime: Long
    val hasBeenRefreshed
        get() = lastRefreshTime > 0L

    fun refresh(scope: CoroutineScope = GlobalScope, refreshContext: CoroutineContext = Dispatchers.IO)
    suspend fun valueElseRefresh(
        within: Duration = Duration.INFINITE,
        refreshContext: CoroutineContext = Dispatchers.IO,
    ): T

    fun isWithinDuration(within: Duration): Boolean

    companion object {

        operator fun <T> invoke(
            initialValue: ResultState<T> = ResultState.Empty(),
            now: () -> Long = System::currentTimeMillis,
            forceRefreshIf: suspend RefreshStateFlow<ResultState<T>>.(T) -> Boolean = {
                it == null
            },
            onRefresh: suspend CoroutineScope.() -> ResultState<T>,
        ) = create(initialValue = initialValue,
            now = now,
            initialRefreshTime = initialValue.onResult { now() } ?: 0L,
            forceRefreshIf = {
                when (it) {
                    !is GitResult.Success -> true
                    else -> forceRefreshIf(it.value)
                }
            },
            setRefreshTime = {
                it.onResult(success = { now() }) ?: 0L
            },
            onRefresh = onRefresh)

        operator fun <T> invoke(
            initialValue: T,
            now: () -> Long = System::currentTimeMillis,
            initialRefreshTime: Long = initialValue?.let { now() } ?: 0L,
            forceRefreshIf: suspend RefreshStateFlow<T>.(T) -> Boolean = {
                it == null
            },
            onRefresh: suspend CoroutineScope.() -> T,
        ): RefreshStateFlow<T> = create(initialValue, now, initialRefreshTime, forceRefreshIf, onRefresh = onRefresh)


    }
}

private fun Any?.timeStampNotNull(now: () -> Long) = this?.let { now() } ?: 0L


private fun <T> create(
    initialValue: T,
    now: () -> Long = System::currentTimeMillis,
    initialRefreshTime: Long = initialValue.timeStampNotNull(now),
    forceRefreshIf: suspend RefreshStateFlow<T>.(T) -> Boolean = {
        it == null
    },
    setRefreshTime: (T) -> Long = { now() },
    onRefresh: suspend CoroutineScope.() -> T,
): RefreshStateFlow<T> {
    val mutableStateFlow = MutableStateFlow(initialValue)
    return object : RefreshStateFlow<T>, StateFlow<T> by mutableStateFlow {

        override var lastRefreshTime = initialRefreshTime
            private set

        override fun isWithinDuration(within: Duration): Boolean {
            return (now() - this.lastRefreshTime < within.inMilliseconds)
        }

        override fun refresh(scope: CoroutineScope, refreshContext: CoroutineContext) {
            scope.launch(refreshContext) {
                valueElseRefresh(Duration.ZERO, coroutineContext)
            }
        }

        override suspend fun valueElseRefresh(
            within: Duration,
            refreshContext: CoroutineContext,
        ): T {
            suspend fun refresh() = withContext(refreshContext) {
                val value = onRefresh()
                setRefreshTime(value)
                mutableStateFlow.value = value
                mutableStateFlow.value
            }
            if (!isWithinDuration(within)) return refresh()
            return when (forceRefreshIf(value)) {
                true -> refresh()
                else -> value
            }
        }
    }
}

