package com.example.endpoint.routing.base

import kotlinx.coroutines.newFixedThreadPoolContext
import java.time.Duration
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

open class  Base<T> {
    var incA = AtomicInteger()
    val eda = CopyOnWriteArrayList<T>()
    //constructor
    init {  }
    //static scope
    companion object { }
}