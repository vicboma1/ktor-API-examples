package com.example.endpoint.routing

import com.example.endpoint.utils.*
import endpoint.routing.users.UserHandler
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

const val REST_ENDPOINT_USER = "/user"

//https://ktor.io/servers/features/routing.html
@ExperimentalUnsignedTypes
fun Application.routingUser(userHandler:UserHandler) {
    //basic CRUD
    routing {
        getId(userHandler)
        deleteId(userHandler)
        post(userHandler)
        put(userHandler)
    }
}

private fun Routing.put(userHandler:UserHandler) {
    put(REST_ENDPOINT_USER) {
        safetyAsync {
            //alternativa al when check is null
            userHandler.put(call.receive())
                ?.let {
                    call.resolveAsync(it)
                } ?:
                run {
                    call.rejectNotFoundAsync("User not found")
                }
        }
    }
}

private fun Routing.post(userHandler:UserHandler) {
    post(REST_ENDPOINT_USER) {
        safetyAsync {
            val receive = userHandler.add(call.receive())
            println("ReceivedPost Request: $receive")
            call.resolveCreateAsync(receive)
        }
    }
}

@ExperimentalUnsignedTypes
private fun Routing.deleteId(userHandler:UserHandler) {
    delete("$REST_ENDPOINT_USER/{id}") {
        safetyAsync {
            when (val id = call.parameters["id"]?.toULongOrNull() ){
                null -> call.rejectBadRequestAsync("Paramenter id not found" )
                else ->
                    when(userHandler.remove(id.toString())){
                        true-> call.resolveAsync("User $id deleted")
                        else -> call.rejectNotFoundAsync("User $id no found")
                    }
            }
        }
    }
}

private fun Routing.getId(userHandler:UserHandler) {
    get("$REST_ENDPOINT_USER/{id}") {
        safetyAsync {
            when(val id = call.parameters["id"]?.toIntOrNull()){ //Si escala, poner un Long
                null ->  call.rejectBadRequestAsync("id is null")
                else ->  when(val user = userHandler.get(id.toString())){
                    null ->  call.rejectNotFoundAsync("User $id is not found")
                    else ->  call.resolveAsync(user)
                }
            }
        }
    }
}