package com.example

import app.main
import com.example.endpoint.routing.REST_ENDPOINT_USER
import endpoint.routing.users.UserHandler
import io.ktor.http.*
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlin.test.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@UseExperimental(ExperimentalUnsignedTypes::class)
class ApplicationTest {

    var userHandlerMock : UserHandler? = null

    @BeforeTest
    fun prepare() {
        userHandlerMock = UserHandler()
    }

    @AfterTest
    fun tearDown() {
        userHandlerMock = null
    }

    @Test
    fun testRoot() {
        withTestApplication({ main(userHandlerMock!!) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertNull(response.status())
            }
        }
    }

    @Test
    fun testException() {
        userHandlerMock = null
        val exception = assertFailsWith<IllegalArgumentException> {
            withTestApplication({ main(userHandlerMock ?: throw IllegalArgumentException("userHandler is Null")) }) {
            }
        }
        assertEquals("userHandler is Null", exception.message)
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
        delete("del",HttpStatusCode.BadRequest,"{\n" + "  \"error\": \"id is null\"\n" + "}")
    }

    @Test
    fun testDeleteNotFoundRequest() {
        delete("1",HttpStatusCode.NotFound,"{\n" + "  \"error\": \"User 1 is not found\"\n" + "}")
    }

    @Test
    fun testDeleteOKRequest() {
        post(1)
        delete("1",HttpStatusCode.OK,"{\n" +"  \"id\": 1,\n" + "  \"name\": \"vicboma1\",\n" + "  \"age\": 33\n" + "}")
    }

    private fun delete(id:String, code: HttpStatusCode, expected :String) =
        withTestApplication({ main(userHandlerMock!!) }) {
            handleRequest(HttpMethod.Get, "$REST_ENDPOINT_USER/$id").apply {
                assertEquals(code, response.status())
                assertEquals(expected, response.content)
            }
        }

    private fun get(id:String, code: HttpStatusCode, expected :String) =
        withTestApplication({ main(userHandlerMock!!) }) {
            handleRequest(HttpMethod.Get, "$REST_ENDPOINT_USER/$id").apply {
                assertEquals(code, response.status())
                assertEquals(expected, response.content)
            }
        }

    private fun post(index: Int) =
        withTestApplication({main(userHandlerMock!!) }) {
            val jsonBodyRequest = "{\n  \"name\": \"vicboma$index\",\n  \"age\": 33\n}"
            val jsonBodyResponse = "{\n  \"id\": $index,\n  \"name\": \"vicboma$index\",\n  \"age\": 33\n}"

            handleRequest(HttpMethod.Post, REST_ENDPOINT_USER) {
                addHeader(HttpHeaders.ContentType, "application/json; charset=UTF-8")
                setBody(jsonBodyRequest)
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                assertEquals(jsonBodyResponse, response.content)
            }

        }

    private fun put(jsonBodyRequest :String, jsonBodyResponse: String ) =
        withTestApplication({main(userHandlerMock!!) }) {
            handleRequest(HttpMethod.Put, REST_ENDPOINT_USER) {
                addHeader(HttpHeaders.ContentType, "application/json; charset=UTF-8")
                setBody(jsonBodyRequest)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(jsonBodyResponse, response.content)
            }
        }
    }

