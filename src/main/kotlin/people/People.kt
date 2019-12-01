package people

import java.lang.IllegalArgumentException
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

object People {
    private val _counter = AtomicInteger()
    private val _users = CopyOnWriteArrayList<User>()

    //Adding new user o return the input user if contains it
    fun add(user : User) : User {
        if(_users.contains(user))
            return _users.find { it == user }!!

        user.id = _counter.incrementAndGet()
        _users.add(user)
        return user
    }

    //
    fun get(id:String) =
            _users.find { it.id.toString() == id }
                    ?: throw IllegalArgumentException("No user found for $id")

    //
    fun get(id:Int)  = get(id.toString())

}