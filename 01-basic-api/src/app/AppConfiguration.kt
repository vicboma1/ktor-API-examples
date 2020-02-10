package app

import com.example.endpoint.routing.routingUser
import endpoint.routing.users.UserHandler
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CORS.Feature.CORS_DEFAULT_MAX_AGE
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.maxAge
import io.ktor.gson.gson
import java.text.DateFormat
import java.time.Duration

@ExperimentalUnsignedTypes
fun Application.main(userHandler: UserHandler){

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
        maxAgeInSeconds = 24L * 3600 //24L * 3600 = 1 día | (60 * 60 * 24) = 86400L 1 año
      //  maxAge = Duration.ofDays(1)
    }

    //https://ktor.io/servers/features/routing.html
    routingUser(userHandler)
}

