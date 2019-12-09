package com.example.endpoint.routing.routingUser

import app.main
import io.ktor.client.*
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.*
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

@KtorExperimentalLocationsAPI
class ApplicationAuthTest {

    @Test
    fun testRoot() {
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK,response.status())
                assertEquals("""
                    <!DOCTYPE html>
                    <html>
                      <body>
                        <ul>
                          <li><a href="/secure">/secure</a></li>
                          <li><a href="/admin/user/vicboma">/admin/user/vicboma</a></li>
                        </ul>
                      </body>
                    </html>
                    
                """.trimIndent(),response.content)
            }
        }
    }

    private val Url.portParam: String get() = if (port == protocol.defaultPort) host else hostWithPort
    private val Url.absolute: String get() = "${protocol.name}://$portParam$fullPath"

    @Test
    fun testSecure() {

            withTestApplication {
                val endPoint = "http://127.0.0.1:8080/secure"

                runBlocking {

                    /*       val client = HttpClient() {
                               install(Authentication) {
                                   basic {
                                       username = "vicboma"
                                       password = "12345"
                                   }
                               }
                           }
                    */
                    var cliente = HttpClient(MockEngine) {
                        engine {
                            addHandler { request ->
                                when (request.url.absolute) {
                                    endPoint -> respond("{ \n\"success\": \"vicboma\" \n}")
                                    else -> error("Unhandled ${request.url.absolute}")
                                }
                            }
                        }
                    }

                handleRequest(HttpMethod.Get, "/secure").apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                    assertEquals("{ \n\"success\": \"vicboma\" \n}", response.content)
                }
            }
        }
    }
}