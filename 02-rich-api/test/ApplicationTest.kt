package com.example

import app.main
import com.example.endpoint.routing.routingUser.REST_ENDPOINT_USER
import com.example.endpoint.routing.routingUser.users.UserHandler
import io.ktor.http.*
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ApplicationTest {


    @BeforeTest
    fun prepare() {
        assertNotNull(UserHandler)
    }

    @AfterTest
    fun tearDown() {
        UserHandler.apply {
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
            handleRequest(HttpMethod.Get,
                REST_ENDPOINT_USER
            ).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("[]", response.content)
            }
        }
    }

    @Test
    fun testGetAllResolve() {
        val jsonBodyResponse1 = "[\n" +"  {\n" +"    \"id\": 1,\n" +"    \"name\": \"vicboma1\",\n" +"    \"age\": 33\n" + "  }\n" +"]"
        post(1)
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Get,
                REST_ENDPOINT_USER
            ).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(jsonBodyResponse1, response.content)
            }
        }
    }

    @Test
    fun testGetResolveEmpty() {
        val expected = "{\n" + "  \"error\": \"User 1 is not found\"\n" + "}"
        get("1",HttpStatusCode.NotFound,expected)
    }

    @Test
    fun testGetIdResolve() {
        var index = 1
        val jsonBodyResponse1 = "{\n  \"id\": $index,\n  \"name\": \"vicboma$index\",\n  \"age\": 33\n}"
        post(index)
        get(index.toString(),HttpStatusCode.OK,jsonBodyResponse1)

        index = 2
        val jsonBodyResponse2 = "{\n  \"id\": $index,\n  \"name\": \"vicboma$index\",\n  \"age\": 33\n}"
        post(index)
        get(index.toString(),HttpStatusCode.OK,jsonBodyResponse2)
    }

    @Test
    fun testPostResolve() {
        post(1)
    }

    @Test
    fun testPostTwiceResolve() {
        post(1)
        post(2)
    }

    @Test
    fun testPut() {
        var index = 1
        val jsonBodyResponse1 = "{\n  \"id\": $index,\n  \"name\": \"vicboma$index\",\n  \"age\": 33\n}"
        val jsonBodyResponse = "{\n  \"id\": $index,\n  \"name\": \"vicboma$index\",\n  \"age\": 35\n}"
        val jsonBodyRequest = "{\n  \"name\": \"vicboma$index\",\n  \"age\": 35\n}"

        post(index)
        get(index.toString(),HttpStatusCode.OK, jsonBodyResponse1)
        put(jsonBodyRequest,jsonBodyResponse)
        get(index.toString(),HttpStatusCode.OK,jsonBodyResponse)
    }

    @Test
    fun testDeleteBadRequest() {
        delete("del",HttpStatusCode.BadRequest,"{\n" + "  \"error\": \"Bad request\"\n" + "}")
    }

    @Test
    fun testDeleteNotFoundRequest() {
        delete("1",HttpStatusCode.NotFound,"{\n" + "  \"error\": \"User 1 no found\"\n" + "}")
    }

    @Test
    fun testDeleteOKRequest() {
        post(1)
        delete("1",HttpStatusCode.OK,"{\n" + "  \"success\": \"User 1 deleted\"\n" + "}")
    }

    @Test
    fun testDeleteAllRequest() {
        post(1)
        post(2)
        deleteAll(HttpStatusCode.OK,"[]")
    }

    @Test
    fun testGetAllRequest() {
        post(1)
        post(2)
        getAll(HttpStatusCode.OK,"[\n" +"  {\n" +"    \"id\": 1,\n" +"    \"name\": \"vicboma1\",\n" + "    \"age\": 33\n" + "  },\n" +"  {\n" +"    \"id\": 2,\n" +"    \"name\": \"vicboma2\",\n" + "    \"age\": 33\n" + "  }\n" +"]")
    }

    private fun delete(id:String, code: HttpStatusCode, expected :String) =
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Delete, "$REST_ENDPOINT_USER/$id").apply {
                assertEquals(code, response.status())
                assertEquals(expected, response.content)
            }
        }

    private fun deleteAll(code: HttpStatusCode, expected :String) =
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Delete, "$REST_ENDPOINT_USER").apply {
                assertEquals(code, response.status())
                assertEquals(expected, response.content)
            }
        }

    private fun get(id:String, code: HttpStatusCode, expected :String) =
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Get, "$REST_ENDPOINT_USER/$id").apply {
                assertEquals(code, response.status())
                assertEquals(expected, response.content)
            }
        }

    private fun getAll(code: HttpStatusCode, expected :String) =
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Get, "$REST_ENDPOINT_USER").apply {
                assertEquals(code, response.status())
                assertEquals(expected, response.content)
            }
        }

    private fun post(index: Int) =
        withTestApplication({ main() }) {
            val jsonBodyRequest = "{\n  \"name\": \"vicboma$index\",\n  \"age\": 33\n}"
            val jsonBodyResponse = "{\n  \"id\": $index,\n  \"name\": \"vicboma$index\",\n  \"age\": 33\n}"

            handleRequest(HttpMethod.Post,
                REST_ENDPOINT_USER
            ) {
                addHeader(HttpHeaders.ContentType, "application/json; charset=UTF-8")
                setBody(jsonBodyRequest)
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                assertEquals(jsonBodyResponse, response.content)
            }

        }

    private fun put(jsonBodyRequest :String, jsonBodyResponse: String ) =
        withTestApplication({ main() }) {
            handleRequest(HttpMethod.Put,
                REST_ENDPOINT_USER
            ) {
                addHeader(HttpHeaders.ContentType, "application/json; charset=UTF-8")
                setBody(jsonBodyRequest)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(jsonBodyResponse, response.content)
            }
        }
    }

