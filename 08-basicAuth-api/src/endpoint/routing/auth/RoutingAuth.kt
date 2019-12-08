package com.example.endpoint.routing.auth

import com.example.endpoint.utils.ResourceUtils.getResourceAsFile
import com.example.endpoint.utils.grayscaleImage
import com.example.endpoint.utils.resolveAsync
import com.example.endpoint.utils.threadPool
import io.ktor.application.call
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.principal
import io.ktor.html.respondHtml
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.route
import kotlinx.coroutines.withContext
import kotlinx.html.*
import org.apache.commons.codec.binary.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO


private const val SLASH = "/"
const val AUTH = "auth"
const val REST_ENDPOINT_SECURE = "${SLASH}secure"
const val REST_ENDPOINT_SECURE_ADMIN= "/admin/*"
const val REST_ENDPOINT_SECURE_ADMIN_USER_VICBOMA = "vicboma"
const val REST_ENDPOINT_SECURE_ADMIN_VICBOMA = "/admin/user/vicboma"

@KtorExperimentalLocationsAPI
  fun Route.getAuth() {
    get(SLASH){
        call.respondHtml {
            body {
                ul {
                    for (item in listOf(REST_ENDPOINT_SECURE,REST_ENDPOINT_SECURE_ADMIN_VICBOMA)) {
                        li { a(href = item) { +item } }
                    }
                }
            }
        }
    }

    authenticate(AUTH){
        get(REST_ENDPOINT_SECURE) {
            call.resolveAsync(call.principal<UserIdPrincipal>()?.name!!)
        }

        route(REST_ENDPOINT_SECURE_ADMIN) {
            get(REST_ENDPOINT_SECURE_ADMIN_USER_VICBOMA) {
                call.resolveAsync(call.principal<UserIdPrincipal>().toString())
            }
        }

    }

  }
