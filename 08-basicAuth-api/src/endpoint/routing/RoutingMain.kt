package com.example.endpoint.routing

import com.example.endpoint.routing.auth.getAuth
import com.example.endpoint.routing.routingColor.*
import com.example.endpoint.routing.routingFlow.REST_ENDPOINT_FLOW
import com.example.endpoint.routing.routingFlow.*
import com.example.endpoint.routing.routingNumber.*
import com.example.endpoint.routing.routingUser.*
import io.ktor.application.Application
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.routing.Routing
import io.ktor.routing.route
import io.ktor.routing.routing

//https://ktor.io/servers/features/routing.html
@KtorExperimentalLocationsAPI
fun Application.routingMain() {
    //rich CRUD
    routing {
        routeFlow()
        routeNumber()
        routeUser()
        routeColor()
        configureStatic()
        routeAuth()
    }
}

@KtorExperimentalLocationsAPI
private fun Application.routeUser() {
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
private fun Routing.routeNumber() {
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

@KtorExperimentalLocationsAPI
private fun Routing.routeFlow() {
    route(REST_ENDPOINT_FLOW) {
        getAllFlowId()
        getAllBlockFlowId()
        getAllwithContextFlowId()
        getAllFlowIdOdd()
        getAllBlockFlowIdOdd()
        getAllwithContextFlowIdOdd()
        getAllFlowIdEven()
        getAllBlockFlowIdEven()
        getAllwithContextFlowIdEven()
    }
}

@KtorExperimentalLocationsAPI
private fun Routing.routeColor() {
    route(REST_ENDPOINT_COLOR) {
        putColorMono()
    }
}


@KtorExperimentalLocationsAPI
private fun Routing.configureStatic() {
    static("/static/burguer"){
        resources("static/burguer")
    }
}

@KtorExperimentalLocationsAPI
private fun Application.routeAuth() {
   routing {
       getAuth()
   }
}

