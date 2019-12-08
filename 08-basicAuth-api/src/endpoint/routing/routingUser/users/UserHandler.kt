package com.example.endpoint.routing.routingUser.users

import com.example.endpoint.routing.base.Base

object UserHandler : Base<User>() {

    fun get(id:String?) =
           eda.find { it?.id.toString() == id }

    fun get(id:Int?)  =
        get(id!!.toString())

    fun getAllList() =
        eda.toList()

    fun getAllArray() =
        eda.toArray()

    fun remove(user: User) =
        when(eda.contains(user)){
            true -> eda.let {
                        it.remove(user);
                        user
                }
            else -> null
        }

    fun remove(id:String) =
        eda.remove(get(id))

    fun remove (id:Int) =
        eda.remove(get(id))

    fun clear() =
        eda.clear()

    fun add(user : User)  =
        when(eda.contains(user)) {
            true -> eda.find { it == user }!!
            else -> eda.let {
                    it.add(
                        user.let {
                            it.id = incA.incrementAndGet()
                            it
                        })
                    user
            }
        }

    fun put(user: User): User? =
         when(eda.contains(user)){
            true -> eda.let {
                val index = it.indexOf(user);
                user.id = it[index].id
                it[index] = user
                it[index]
            }
            else -> null
        }

}