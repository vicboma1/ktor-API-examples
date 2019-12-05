package com.example.endpoint.routing.routingUser

import com.example.endpoint.routing.routingUser.users.*
import com.example.endpoint.utils.*
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.content.MultiPartData
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.routing.*

private const val SLASH = "/"
const val REST_ENDPOINT_USER = "${SLASH}user"
const val REST_ENDPOINT_USER_ID = "$REST_ENDPOINT_USER${SLASH}{id}"

//https://ktor.io/servers/features/routing.html
@KtorExperimentalLocationsAPI
fun Application.routingUser() {
    //rich CRUD
    routing {
            getId()
            getAll()
            deleteId()
            deleteAll()
            post()
            put()
    }
}

@KtorExperimentalLocationsAPI
private fun Route.put() {
    put<User>{
        safetyAsync {
            when(val userUp = UserHandler.put(call.receive())){
                null -> call.rejectNotFoundAsync("User not found")
                else -> call.resolveAsync(userUp)
            }
        }
    }
}

@KtorExperimentalLocationsAPI
private fun Route.post() {
    post<User> {
        safetyAsync {
            val user = UserHandler.add(call.receive())
            println("ReceivedPost Request: $user")
            call.resolveCreateAsync(user)
        }
    }
}

@KtorExperimentalLocationsAPI
private fun Route.deleteAll() {
    delete<User> {
        safetyAsync {
            UserHandler.clear()
            call.resolveAsync(UserHandler.getAllArray())
        }
    }
}

@KtorExperimentalLocationsAPI
private fun Route.deleteId() {
    delete<UserId> {
        safetyAsync {
            when (val id = it?.id.toString().toLongOrNull()){
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
@KtorExperimentalLocationsAPI
private fun Route.getAll() {
    get<User> {
        safetyAsync {
            call.resolveAsync(UserHandler.getAllArray())
        }
    }
}

@KtorExperimentalLocationsAPI
private fun Route.getId() {
    get<UserId>{
        safetyAsync {
            when(val id = it.id.toString()?.toLongOrNull()){
                null ->  call.rejectBadRequestAsync("id is null")
                else ->  when(val user = UserHandler.get(id.toString())){
                    null ->  call.rejectNotFoundAsync("User $id is not found")
                    else -> call.resolveAsync(user)
                }
            }
        }
    }
}