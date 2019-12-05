package com.example.endpoint.routing.routingNumber

import com.example.endpoint.routing.base.delayBlock
import com.example.endpoint.routing.routingUser.users.*
import com.example.endpoint.utils.*
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.routing.*

private const val SLASH = "/"
const val REST_ENDPOINT_NUMBER = "${SLASH}number"
const val REST_ENDPOINT_NUMBER_ID = "${SLASH}{id}"
const val REST_ENDPOINT_NUMBER_MINOR = "${SLASH}minor"
const val REST_ENDPOINT_NUMBER_MAJOR = "${SLASH}major"
const val REST_ENDPOINT_NUMBER_EQUALS = "${SLASH}equals"
const val REST_ENDPOINT_APPEND_NUMBERS = "${SLASH}add"

//https://ktor.io/servers/features/routing.html
@KtorExperimentalLocationsAPI
fun Application.routingNumber() {
    //rich CRUD
    routing {
        route(REST_ENDPOINT_NUMBER) {
            getAllAsyncWithContext()
            getIdAsyncWithContext()
            postMajorAsyncWithContext()
            postMinorAsyncWithContext()
            postEqualsAsyncWithContext()
            deleteIdAsyncWithContext()
            deleteAllAsyncWithContext()
            postAsyncWithContext()
        }
    }
}

@KtorExperimentalLocationsAPI
private fun Route.postAsyncWithContext () {
    post(REST_ENDPOINT_APPEND_NUMBERS) {
        safetyAsyncWithContext {
            when(val id = call.receive<com.example.endpoint.routing.routingNumber.Number>().id){
                null ->  call.rejectBadRequestAsync("id is null")
                else ->  {
                    when(val array = appendNumbers(id)){
                        null ->  call.rejectNotFoundAsync("Number $id is not found")
                        else -> call.resolveCreateAsync(array)
                    }
                }
            }
        }
    }
}

@KtorExperimentalLocationsAPI
private fun Route.deleteAllAsyncWithContext () {
    delete {
        safetyAsyncWithContext {
            delayBlock(100)
            NumberHandler.clear()
            call.resolveAsync(NumberHandler.getAllArray())
        }
    }
}

@KtorExperimentalLocationsAPI
private fun Route.deleteIdAsyncWithContext () {
    delete(REST_ENDPOINT_NUMBER_ID) {
        safetyAsyncWithContext {
            when (val id = call.parameters["id"]?.toULongOrNull()){
                null -> call.rejectBadRequestAsync("Bad request" )
                else -> {
                    delayBlock(25)
                    when (NumberHandler.remove(id.toString())) {
                        true -> call.resolveAsync("Number $id deleted")
                        else -> call.rejectNotFoundAsync("Number $id no found")
                    }
                }
            }
        }
    }
}
@KtorExperimentalLocationsAPI
private fun Route.getAllAsyncWithContext () {
    get {
        safetyAsyncWithContext {
            call.resolveAsync(NumberHandler.getAllList())
        }
    }
}

@KtorExperimentalLocationsAPI
private fun Route.postMajorAsyncWithContext () {
    post(REST_ENDPOINT_NUMBER_MAJOR){
        safetyAsyncWithContext {
            when(val id = call.receive<com.example.endpoint.routing.routingNumber.Number>().id){
                null ->  call.rejectBadRequestAsync("id is null")
                else ->  {
                    when(val majors = NumberHandler.getMajor(id)){
                        null ->  call.rejectNotFoundAsync("Number $id is not found")
                        else -> call.resolveAsync(majors)
                    }
                }
            }
        }
    }
}

@KtorExperimentalLocationsAPI
private fun Route.postMinorAsyncWithContext () {
    post(REST_ENDPOINT_NUMBER_MINOR){
        safetyAsyncWithContext {
            when(val id = call.receive<com.example.endpoint.routing.routingNumber.Number>().id){
                null ->  call.rejectBadRequestAsync("id is null")
                else ->  when(val minors = NumberHandler.getMinor(id)){
                    null ->  call.rejectNotFoundAsync("Number $id is not found")
                    else -> call.resolveAsync(minors)
                }
            }
        }
    }
}


@KtorExperimentalLocationsAPI
private fun Route.postEqualsAsyncWithContext () {
    post(REST_ENDPOINT_NUMBER_EQUALS){
        safetyAsyncWithContext {
            when(val id = call.receive<com.example.endpoint.routing.routingNumber.Number>().id){
                null ->  call.rejectBadRequestAsync("id is null")
                else ->  when(val _equals = NumberHandler.getEquals(id)){
                    null ->  call.rejectNotFoundAsync("Number $id is not found")
                    else -> call.resolveAsync(_equals)
                }
            }
        }
    }
}

@KtorExperimentalLocationsAPI
private fun Route.getIdAsyncWithContext () {
    get(REST_ENDPOINT_NUMBER_ID){
        safetyAsyncWithContext {
            when(val id = call.parameters["id"]?.toIntOrNull()){
                null ->  call.rejectBadRequestAsync("id is null")
                else ->  call.resolveAsync(NumberHandler.get(id)?.toString() ?: false)
            }
        }
    }
}

private fun appendNumbers(size:Int): Array<out Any>? {
    for (i in 0..size) {
        delayBlock(20)
        NumberHandler.add(i)
    }
    return NumberHandler.getAllArray()
}