package com.example.endpoint.routing.routingUser.users

import com.example.endpoint.routing.base.Base

object UserHandler : Base() {

    fun get(id:String?) =
           uh.find { it?.id.toString() == id }

    fun get(id:Int?)  =
        get(id!!.toString())

    fun getAllList() =
        uh.toList()

    fun getAllArray() =
        uh.toArray()

    fun remove(user: User) =
        when(uh.contains(user)){
            true -> uh.let {
                        it.remove(user);
                        user
                }
            else -> null
        }

    fun remove(id:String) =
        uh.remove(get(id))

    fun remove (id:Int) =
        uh.remove(get(id))

    fun clear() =
        uh.clear()

    fun add(user : User)  =
        when(uh.contains(user)) {
            true -> uh.find { it == user }!!
            else -> uh.let {
                    it.add(
                        user.let {
                            it.id = incA.incrementAndGet()
                            it
                        })
                    user
            }
        }

    fun put(user: User): User? =
         when(uh.contains(user)){
            true -> uh.let {
                val index = it.indexOf(user);
                user.id = it[index].id
                it[index] = user
                it[index]
            }
            else -> null
        }

}