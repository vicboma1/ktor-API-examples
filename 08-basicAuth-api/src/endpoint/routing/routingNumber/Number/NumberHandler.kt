package com.example.endpoint.routing.routingUser.users

import com.example.endpoint.routing.base.Base

object NumberHandler : Base<Int>() {

    fun get(id:String?) =
           eda.find { it?.toString() == id }

    fun get(id:Int)  =
        get(id.toString())

    fun getMajor(id:Int)  =
        eda.filter { it.compareTo(id) > 0 }
            .map{ it }
            .stream()
            .toArray()

    fun getMinor(id:Int)  =
        eda.filter { it.compareTo(id) < 0 }
            .map{ it }
            .stream()
            .toArray()

    fun getEquals(id:Int)  =
        eda.filter { it.compareTo(id) == 0 }
            .map{ it }
            .stream()
            .toArray()

    fun getAllList() =
        eda.toList()

    fun getAllArray() =
        eda.toArray()

    fun remove(id:String) =
        eda.remove(get(id))

    fun remove (id:Int) =
        eda.remove(get(id))

    fun clear() =
        eda.clear()

    fun add(number : Int)  =
        when(eda.contains(number)) {
            true -> eda.find { it == number }!!
            else -> eda.let {
                    if(!it.contains(number))
                        it.add(number)
                    else
                        false
            }
        }
}