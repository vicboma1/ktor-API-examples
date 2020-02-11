package app

import com.example.endpoint.routing.routingMain
import com.example.install.installAuth
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic
import io.ktor.features.AutoHeadResponse
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import java.text.DateFormat
import java.time.Duration

@KtorExperimentalLocationsAPI
fun Application.main(){
    //https://ktor.io/servers/features/authentication.html
    installAuth()
    //https://ktor.io/servers/features/autoheadresponse.html
    install(AutoHeadResponse)
    //https://ktor.io/servers/features/content-negotiation.html
    install(ContentNegotiation){
        gson{
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
    //https://ktor.io/servers/features/cors.html
    install(CORS){
        maxAgeInSeconds = 60 * 60 *24
    }
    //https://ktor.io/servers/features/locations.html
    install(Locations)
    //https://ktor.io/servers/features/routing.html
    routingMain()
}

