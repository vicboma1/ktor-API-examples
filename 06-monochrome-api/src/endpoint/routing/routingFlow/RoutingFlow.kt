package com.example.endpoint.routing.routingFlow

import com.example.endpoint.utils.*
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.withContext
import java.io.IOException

private const val SLASH = "/"
private const val ID = "$SLASH{id}"
const val REST_ENDPOINT_FLOW = "${SLASH}flow"
const val REST_ENDPOINT_FLOW_BLOCK = "${SLASH}block$ID"
const val REST_ENDPOINT_FLOW_CONTEXT = "${SLASH}context$ID"

const val REST_ENDPOINT_FLOW_ODD = "${SLASH}odd$ID"
const val REST_ENDPOINT_FLOW_BLOCK_ODD =  "${SLASH}block${SLASH}odd$ID"
const val REST_ENDPOINT_FLOW_CONTEXT_ODD =  "${SLASH}context${SLASH}odd$ID"

const val REST_ENDPOINT_FLOW_EVEN = "${SLASH}even$ID"
const val REST_ENDPOINT_FLOW_BLOCK_EVEN =  "${SLASH}block${SLASH}even$ID"
const val REST_ENDPOINT_FLOW_CONTEXT_EVEN =  "${SLASH}context${SLASH}even$ID"

@KtorExperimentalLocationsAPI
  fun Route.getAllFlowId() {
    get(ID) {
        safetyAsync {
            call.resolveAsync(createFlow())
        }
    }
}

@KtorExperimentalLocationsAPI
  fun Route.getAllBlockFlowId() {
    get(REST_ENDPOINT_FLOW_BLOCK) {
        safetyAsync {
            runBlocking {
                call.resolveAsync(createFlow())
            }
        }
    }
}

//Wrong emission withContext
@KtorExperimentalLocationsAPI
  fun Route.getAllwithContextFlowId() {
    get(REST_ENDPOINT_FLOW_CONTEXT) {
        safetyAsync {
            withContext(threadPool) {
                call.resolveAsync(createFlow())
            }
        }
    }
}

@KtorExperimentalLocationsAPI
fun Route.getAllFlowIdOdd() {
    get(REST_ENDPOINT_FLOW_ODD) {
        safetyAsync {
            call.resolveAsync(createFlow(1))
        }
    }
}

@KtorExperimentalLocationsAPI
fun Route.getAllBlockFlowIdOdd() {
    get(REST_ENDPOINT_FLOW_BLOCK_ODD) {
        safetyAsync {
            runBlocking {
                call.resolveAsync(createFlow(1))
            }
        }
    }
}

//Wrong emission withContext
@KtorExperimentalLocationsAPI
fun Route.getAllwithContextFlowIdOdd() {
    get(REST_ENDPOINT_FLOW_CONTEXT_ODD) {
        safetyAsync {
            withContext(threadPool) {
                call.resolveAsync(createFlow(1))
            }
        }
    }
}

@KtorExperimentalLocationsAPI
fun Route.getAllFlowIdEven() {
    get(REST_ENDPOINT_FLOW_EVEN) {
        safetyAsync {
            call.resolveAsync(createFlow(0))
        }
    }
}

@KtorExperimentalLocationsAPI
fun Route.getAllBlockFlowIdEven() {
    get(REST_ENDPOINT_FLOW_BLOCK_EVEN) {
        safetyAsync {
            runBlocking {
                call.resolveAsync(createFlow(0))
            }
        }
    }
}

//Wrong emission withContext
@KtorExperimentalLocationsAPI
fun Route.getAllwithContextFlowIdEven() {
    get(REST_ENDPOINT_FLOW_CONTEXT_EVEN) {
        safetyAsync {
            withContext(threadPool) {
                call.resolveAsync(createFlow(0))
            }
        }
    }
}

  suspend fun PipelineContext<Unit, ApplicationCall>.createFlow(op:Int) =
      (0..(call.parameters["id"]?.toInt() ?: throw Exception("No input valid")))
          .asFlow()
       //   .onEach { kotlinx.coroutines.delay((1..20).random().toLong()) }
          .filter { it%2 == op }
          .toList()

suspend fun PipelineContext<Unit, ApplicationCall>.createFlow() =
    (0..(call.parameters["id"]?.toInt() ?: throw Exception("No input valid")))
        .asFlow()
        .toList()

