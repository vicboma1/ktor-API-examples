package endpoint.routing.users

import com.example.endpoint.routing.base.Base

class UserHandler : Base() {
    init {}
    companion object {}

    fun get(id:String?) = uh.find { it?.id.toString() == id }

    fun remove(id:String) = uh.remove(get(id))

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

    fun put(user: User ): User? =
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