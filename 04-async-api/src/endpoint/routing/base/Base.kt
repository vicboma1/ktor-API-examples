package com.example.endpoint.routing.base

import com.example.endpoint.routing.routingUser.users.User
import kotlinx.coroutines.newFixedThreadPoolContext
import java.time.Duration
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

val threadPool = newFixedThreadPoolContext(4, "04-async-api")

fun delayBlock(ofMillis:Long) {
    suspend {
        kotlinx.coroutines.time.delay(Duration.ofMillis(ofMillis))
    }
}

open class  Base<T> {
    var incA = AtomicInteger()
    val eda = CopyOnWriteArrayList<T>()
    //constructor
    init {  }
    //static scope
    companion object { }
}