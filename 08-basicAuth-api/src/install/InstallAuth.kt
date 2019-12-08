package com.example.install

import com.example.endpoint.routing.auth.AUTH
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic

fun Application.installAuth() {
    install(Authentication) {
        basic(AUTH) {
            realm = "Basic Auth"
            validate {

                if (it.password == "12345")
                    UserIdPrincipal(it.name)
                else
                    null
            }
        }
    }
}
