package com.example.endpoint.routing.base

import com.example.endpoint.routing.routingUser.users.User
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

open class Base {
    var incA = AtomicInteger()
    val uh = CopyOnWriteArrayList<User>()
    //constructor
    init {  }
    //static scope
    companion object { }
}