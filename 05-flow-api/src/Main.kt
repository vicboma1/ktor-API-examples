package com.example

import app.main
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

val portName = "--server.port"
val defaultPort = 8080
val hostName = "--server.host"
val defaultHost = "127.0.0.1"

@KtorExperimentalLocationsAPI
fun main(args: Array<String>) {
    embeddedServer(Netty,
        applicationEngineEnvironment {
            module{
                main()
            }
            connector {
                host = loadHostParams(args)
                port = loadPortParam(args)
            }
        }).start(true)
}

private fun loadPortParam(args: Array<String>) =
    loadParam(args,  defaultPort, portName)!!

private fun loadHostParams(args: Array<String>) =
    loadParam(args,  defaultHost, hostName)!!

private fun <R> loadParam(args: Array<String>, _defaultParam : R, param :String ) =
    when (args.isNotEmpty() && args[0].startsWith(param)) {
        false -> _defaultParam
        else ->  args[0].split("=").last().trim() as? R
    }
