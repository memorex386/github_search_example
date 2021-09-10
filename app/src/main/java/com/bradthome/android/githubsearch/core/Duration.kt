package com.bradthome.android.githubsearch.core

import java.util.concurrent.TimeUnit
import kotlin.math.ceil

//TODO replace with kotlin.time when out of experimental
class Duration(private val interval: Long, private val timeUnit: TimeUnit) {

    val inMilliseconds: Long get() = timeUnit.toMillis(interval)

    companion object {
        val INFINITE = Duration(Long.MAX_VALUE, TimeUnit.DAYS)
        val ZERO = Duration(0, TimeUnit.DAYS)
    }
}

fun Long.fromMillisToDuration(): Duration {
    return Duration(this, TimeUnit.MILLISECONDS)
}

fun Duration?.longerThan(duration: Duration): Boolean {
    if (this == null) {
        return false
    }
    return this.inMilliseconds > duration.inMilliseconds
}

fun Duration.asMinutesSecondsFormat(): String {
    val minutes = this.inMilliseconds / TimeUnit.MINUTES.toMillis(1)
    val seconds = (this.inMilliseconds - TimeUnit.MINUTES.toMillis(minutes)) / TimeUnit.SECONDS.toMillis(1)
    return String.format("%01d:%02d", minutes, seconds)
}

fun Duration.asSecondsFormat(): Int {
    return ceil(
        this.inMilliseconds.toFloat() / TimeUnit.SECONDS.toMillis(1).toFloat()
    ).toInt()
}