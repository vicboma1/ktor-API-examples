package com.example.endpoint.routing.routingUser.users

data class User (val name : String, val age: Int){
    var id : Int? = null

    //Necesario para que las operación routing correctamente
    override fun equals(other: Any?): Boolean =
        when(other is User) {
            true -> other.name == name
            else -> super.equals(other)
        }

    //Necesario para que las operación routing correctamente
    override fun hashCode() =
        name.hashCode()
}