package com.example.endpoint.routing.routingUser.users

import com.example.endpoint.routing.routingUser.REST_ENDPOINT_USER
import com.example.endpoint.routing.routingUser.REST_ENDPOINT_USER_ID

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location(REST_ENDPOINT_USER_ID)  data class UserId( val id: String)

@KtorExperimentalLocationsAPI
@Location(REST_ENDPOINT_USER)  data class User (val name : String ="", val age: Int = 0) {
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