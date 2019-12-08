package endpoint.routing.routingColor

import app.main
import com.example.endpoint.routing.routingColor.REST_ENDPOINT_COLOR
import com.example.endpoint.routing.routingColor.REST_ENDPOINT_COLOR_MONO
import com.example.endpoint.utils.ResourceUtils
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.Test
import java.io.File
import java.nio.file.Files
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@io.ktor.locations.KtorExperimentalLocationsAPI
internal class RoutingColorKtTest {

    @Test
    fun testPutImage() {
        val expected = Files.readAllLines(ResourceUtils.getResourceAsFile("\\resourcesTest\\base64Grey.txt")!!.toPath())[0]
        val imageBase64 = Files.readAllLines(ResourceUtils.getResourceAsFile("\\resourcesTest\\base64.txt")!!.toPath())[0]
        val jsonBodyResponse = "<!DOCTYPE html>\n" +"<html>\n" +"  <body><img alt=\"greyscale\" src=\"data:image/jpeg;base64,${expected.subSequence(0,1024)}"

        put("{\n  \"id\": \"${imageBase64}\"\n}",jsonBodyResponse)

        val res = ResourceUtils.getResourceAsFile("\\resources\\vicboma1Grey.txt").toPath()
    }


    private fun put(jsonBodyRequest :String, jsonBodyResponse: String ) =
        withTestApplication({ main() }) {
            handleRequest(
                HttpMethod.Put, "$REST_ENDPOINT_COLOR$REST_ENDPOINT_COLOR_MONO"
            ) {
                addHeader(HttpHeaders.ContentType, "application/json; charset=UTF-8")
                setBody(jsonBodyRequest)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertTrue(response.content!!.contains(jsonBodyResponse))
            }
        }
}
