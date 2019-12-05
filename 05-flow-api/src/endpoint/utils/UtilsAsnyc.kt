package com.example.endpoint.utils

import kotlinx.coroutines.newFixedThreadPoolContext
import java.time.Duration

val threadPool = newFixedThreadPoolContext(4, "04-async-api")

fun delayBlock(ofMillis:Long) {
    suspend {
        kotlinx.coroutines.time.delay(Duration.ofMillis(ofMillis))
    }
}
