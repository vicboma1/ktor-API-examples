package com.example.endpoint.routing.routingColor

import com.example.endpoint.utils.ResourceUtils.getResourceAsFile
import com.example.endpoint.utils.grayscaleImage
import com.example.endpoint.utils.threadPool
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.request.receive
import io.ktor.routing.Route
import io.ktor.routing.put
import kotlinx.coroutines.withContext
import kotlinx.html.body
import kotlinx.html.img
import org.apache.commons.codec.binary.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO


private const val SLASH = "/"
private const val ID = "$SLASH{id}"
const val REST_ENDPOINT_COLOR = "${SLASH}color"
const val REST_ENDPOINT_COLOR_MONO = "${SLASH}mono"

class mono(val id: Any)

@KtorExperimentalLocationsAPI
  fun Route.putColorMono() {
    put(REST_ENDPOINT_COLOR_MONO) {
            val imageId = call.receive<mono>().id.toString()
            val byteArray = Base64.decodeBase64(imageId)

            withContext(threadPool) {
                grayscaleImage(byteArray, getResourceAsFile("\\resources\\vicboma1Grey.jpg")!!)
            }

        val output = ByteArrayOutputStream()
        ImageIO.write(ImageIO.read(File(System.getProperty("user.dir")+"\\resources\\vicboma1Grey.jpg")), "jpg", output)
        val imgGrey = Base64.encodeBase64String(output.toByteArray())

            call.respondHtml {
                body {
                    img(alt = "greyscale", src ="data:image/jpeg;base64,$imgGrey")
                }
            }
    }
  }
