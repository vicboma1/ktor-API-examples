package app

import com.example.endpoint.routing.routingUser.routingUser
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import java.text.DateFormat
import java.time.Duration

fun Application.main(){
    //https://ktor.io/servers/features/default-headers.html
    install(DefaultHeaders)
    //https://ktor.io/servers/features/content-negotiation.html
    install(ContentNegotiation){
        gson{
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
    //https://ktor.io/servers/features/cors.html
    install(CORS){
        maxAge = Duration.ofDays(1)
    }

    //https://ktor.io/servers/features/routing.html
    routingUser()
}

