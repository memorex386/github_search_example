package com.bradthome.android.githubsearch.ktx

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun isoDate(date: String?) = try {
        date?.let {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(it)
        }
    } catch (e: Exception) {
        null
    }

    fun legibleString(date: Date) = SimpleDateFormat("MMM dd, yyyy").format(date)
}