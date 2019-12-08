package com.example.endpoint.routing.routingNumber

import app.main
import com.example.endpoint.routing.routingUser.users.NumberHandler
import io.ktor.http.*
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@KtorExperimentalLocationsAPI
class ApplicationNumberTest {

    @BeforeTest
    fun prepare() {
        assertNotNull(NumberHandler)
    }

    @AfterTest
    fun tearDown() {
        NumberHandler.apply {
            clear()
            incA = AtomicInteger()
        }
    }

    @Test
    fun testRoot() {
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertNull(response.status())
            }
        }
    }

    @Test
    fun testGetAllResolveEmpty() {
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Get,  REST_ENDPOINT_NUMBER
            ).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("[]", response.content)
            }
        }
    }

    @Test
    fun testGetAllResolve() {
        val jsonBodyResponse =
            toJSON(IntRange(0, 3).iterator())
        add(3)
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Get, REST_ENDPOINT_NUMBER
            ).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(jsonBodyResponse, response.content)
            }
        }
    }

    @Test
    fun testGetResolveEmpty() {
        val expected = false.toString()
        get("1",HttpStatusCode.OK,expected)
    }

    @Test
    fun testGetResolve() {
        add(10)
        val expected = 1.toString()
        get("1",HttpStatusCode.OK,expected)
    }

    @Test
    fun testGetResolveEx() {
        add(10)
        val expected = "{\n" + "  \"error\": \"id is null\"\n" + "}"
        get("zxc",HttpStatusCode.BadRequest,expected)
    }

    @Test
    fun testGetIdResolve() {
        add(3)
        get(1.toString(),HttpStatusCode.OK,1.toString())
        get(2.toString(),HttpStatusCode.OK,2.toString())
        get(3.toString(),HttpStatusCode.OK,3.toString())
    }

    @Test
    fun testPostResolve() {
        add(100)
    }

    @Test
    fun testPostTwiceResolve() {
        add(10)
        add(1000)
    }

    @Test
    fun testDeleteBadRequest() {
        delete("del",HttpStatusCode.BadRequest,"{\n" + "  \"error\": \"Bad request\"\n" + "}")
    }

    @Test
    fun testDeleteNotFoundRequest() {
        delete("1",HttpStatusCode.NotFound,"{\n" + "  \"error\": \"Number 1 no found\"\n" + "}")
    }

    @Test
    fun testDeleteOKRequest() {
        add(2)
        delete("1",HttpStatusCode.OK,"{\n" + "  \"success\": \"Number 1 deleted\"\n" + "}")
    }

    @Test
    fun testDeleteAllRequest() {
        add(5)
        deleteAll(HttpStatusCode.OK,"[]")
    }

    @Test
    fun testGetAllRequest() {
        add(50)
        val expected = toJSON(IntRange(0, 50).iterator())
        getAll(HttpStatusCode.OK,expected)
    }

    @Test
    fun testPostMinorRequest() {
        add(50)
        minor(
            HttpStatusCode.OK,
            40,
            toJSON(IntRange(0, 39).iterator())
        )
    }

    @Test
    fun testPostMajorRequest() {
        add(50)
        major(
            HttpStatusCode.OK,
            10,
            toJSON(IntRange(11, 50).iterator())
        )
    }

    @Test
    fun testPosEqualsRequest() {
        add(50)
        equals(
            HttpStatusCode.OK,
            10,
            toJSON(listOf(10).iterator())
        )
    }

    private fun delete(id:String, code: HttpStatusCode, expected :String) =
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Delete, "$REST_ENDPOINT_NUMBER/$id").apply {
                assertEquals(code, response.status())
                assertEquals(expected, response.content)
            }
        }

    private fun deleteAll(code: HttpStatusCode, expected :String) =
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Delete, REST_ENDPOINT_NUMBER).apply {
                assertEquals(code, response.status())
                assertEquals(expected, response.content)
            }
        }

    private fun get(id:String, code: HttpStatusCode, expected :String) =
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Get, "$REST_ENDPOINT_NUMBER/$id").apply {
                assertEquals(code, response.status())
                assertEquals(expected, response.content)
            }
        }

    private fun getAll(code: HttpStatusCode, expected :String) =
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Get, REST_ENDPOINT_NUMBER).apply {
                assertEquals(code, response.status())
                assertEquals(expected, response.content)
            }
        }

    private fun add(size: Int) =
        withTestApplication({ main() }) {
            val jsonBodyResponse =
                toJSON(IntRange(0, size).iterator())
            val jsonBodyRequest = "{\n  \"id\": $size\n}"

            handleRequest(HttpMethod.Post, "$REST_ENDPOINT_NUMBER$REST_ENDPOINT_APPEND_NUMBERS"
            ) {
                addHeader(HttpHeaders.ContentType, "application/json; charset=UTF-8")
                setBody(jsonBodyRequest)
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                assertEquals(jsonBodyResponse, response.content)
            }

        }
    }

    private fun equals(code: HttpStatusCode, size:Int ,expected : String) =
        order(
            code,
            size,
            "$REST_ENDPOINT_NUMBER$REST_ENDPOINT_NUMBER_EQUALS",
            expected
        )

    private fun major(code: HttpStatusCode, size:Int ,expected : String) =
        order(
            code,
            size,
            "$REST_ENDPOINT_NUMBER$REST_ENDPOINT_NUMBER_MAJOR",
            expected
        )

    private fun minor(code: HttpStatusCode, size:Int,expected : String) =
        order(
            code,
            size,
            "$REST_ENDPOINT_NUMBER$REST_ENDPOINT_NUMBER_MINOR",
            expected
        )

    private fun order(code: HttpStatusCode, size: Int, endPoint : String, expected : String) =
        withTestApplication({ main() }) {
            val jsonBodyRequest = "{\n  \"id\": $size\n}"

            handleRequest(HttpMethod.Post, endPoint
            ) {
                addHeader(HttpHeaders.ContentType, "application/json; charset=UTF-8")
                setBody(jsonBodyRequest)
            }.apply {
                assertEquals(code, response.status())
                assertEquals(expected, response.content)
            }
    }

    //Por no meter el GSON xD
    fun toJSON(iterator: Iterator<Int>): String  {
        val sb = StringBuilder();
        sb.append("[\n ");
        while (iterator.hasNext()) {
            var element = iterator.next();
            sb.append(" $element");
            if (iterator.hasNext())
                sb.append(",\n ");
        }
        sb.append("\n]");
        return sb.toString();
    }