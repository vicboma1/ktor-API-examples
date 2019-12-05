package com.example.endpoint.utils

import com.example.endpoint.routing.base.MessageError
import com.example.endpoint.routing.base.MessageSuccess
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.withContext

suspend fun <R> PipelineContext<*, ApplicationCall>.safetyAsync(callback: suspend () -> R) :R?  =
    try {
        callback()
    } catch(e:Exception){
        call.rejectInternalServerErrorAsync(e.toString())
        null
    }

suspend fun <R> PipelineContext<*, ApplicationCall>.safetyAsyncWithContext(callback: suspend () -> R) :R?  =
    try {
        withContext(threadPool) {
            callback()
        }
    } catch(e:Exception){
        call.rejectInternalServerErrorAsync(e.toString())
        null
    }

//semantic resolves
suspend fun <R:Any> ApplicationCall.resolveCreateAsync(res :R) =
    respond(HttpStatusCode.Created, res)

suspend fun <R:Any> ApplicationCall.resolveAsync(res :R) =
    respond(HttpStatusCode.OK, res)

suspend fun ApplicationCall.rejectAsync( status : HttpStatusCode, msg: String ) =
    respond(status, MessageError(msg))

//semantic rejects
suspend fun ApplicationCall.resolveAsync( msg: String ="") =
    resolveAsync(MessageSuccess(msg))

suspend fun ApplicationCall.rejectNotFoundAsync( msg: String ) =
    rejectAsync(HttpStatusCode.NotFound,msg)

suspend fun ApplicationCall.rejectBadRequestAsync(msg: String) =
    rejectAsync(HttpStatusCode.BadRequest, msg)

suspend fun ApplicationCall.rejectInternalServerErrorAsync(msg:String) =
    rejectAsync(HttpStatusCode.InternalServerError, msg)