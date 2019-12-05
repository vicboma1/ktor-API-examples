package com.example.endpoint.routing.routingUser

import com.example.endpoint.utils.*
import com.example.endpoint.routing.routingUser.users.UserHandler
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.*

const val REST_ENDPOINT_USER = "user"

//https://ktor.io/servers/features/routing.html
fun Application.routingUser() {
    //rich CRUD
    routing {
        route(REST_ENDPOINT_USER) {
            getId()
            getAll()
            deleteId()
            delete()
            post()
            put()
        }
    }
}

private fun Route.put() {
    put{
        safetyAsync {
            //alternativa al when -> check is null
            UserHandler.put(call.receive())
                ?.let {
                    call.resolveAsync(it)
                } ?: run {
                    call.rejectNotFoundAsync("User not found")
                }
        }
    }
}

private fun Route.post() {
    post {
        safetyAsync {
            val receive = UserHandler.add(call.receive())
            println("ReceivedPost Request: $receive")
            call.resolveCreateAsync(receive)
        }
    }
}

private fun Route.delete() {
    delete{
        safetyAsync {
            UserHandler.clear()
            call.resolveAsync(UserHandler.getAllArray())
        }
    }
}

private fun Route.deleteId() {
    delete("{id}") {
        safetyAsync {
            when (val id = call.parameters["id"]?.toULongOrNull() ){
                null -> call.rejectBadRequestAsync("Bad request" )
                else ->
                    when(UserHandler.remove(id.toString())){
                        true-> call.resolveAsync("User $id deleted")
                        else -> call.rejectNotFoundAsync("User $id no found")
                    }
            }
        }
    }
}

private fun Route.getAll() {
    get {
        safetyAsync {
            call.resolveAsync(UserHandler.getAllArray())
        }
    }
}

private fun Route.getId() {
    get("{id}") {
        safetyAsync {
            when(val id = call.parameters["id"]?.toLongOrNull()){
                null ->  call.rejectBadRequestAsync("id is null")
                else ->  when(val user = UserHandler.get(id.toString())){
                    null ->  call.rejectNotFoundAsync("User $id is not found")
                    else -> call.resolveAsync(user)
                }
            }
        }
    }
}