# ktor-API-examples

### Ejemplos con KTOR para la creación de un API REST


#### [01 Basic API | CRUD](https://github.com/vicboma1/ktor-API-examples/tree/master/01-basic-api) 
```
    Exposición de una API básica con los elementos operacionales del CRUD
    Operaciones: [ get, put, post, delete]
    PipelineContext semántico
    Bloque SafetyAsync en los elementos del route
```
![](https://github.com/vicboma1/ktor-API-examples/blob/master/00-assets/01-basic-api.png)


#### [02 Rich API | CRUD](https://github.com/vicboma1/ktor-API-examples/tree/master/02-rich-api)
```
    Refactors en el routing anclando el end-point base
    Refactors en el route sin rutas, solo parámetros de entrada
    Exposición de una API enriquecida con los elementos operacionales del CRUD
    Operaciones: [ get, put, post, delete, getAll, deleteAll]
    PipelineContext semántico
    Bloque SafetyAsync en los elementos del route
```
![](https://github.com/vicboma1/ktor-API-examples/blob/master/00-assets/02-rich-api.png)


#### [03 Locations API | type-safe Routing](https://github.com/vicboma1/ktor-API-examples/tree/master/03-locations-api)
```
    Modelos tipados con anotaciones @Locations (refactor friendly)
    Tipado estático para el acceso de diferentes parametros en el route
    Refactors en el routing sin el end-point
    Refactors en el route sin rutas
    Exposición de una API enriquecida con los elementos operacionales del CRUD tipados
    Operaciones: [ get<T>, put<T>, post<T>, delete<T>, getAll<T>, deleteAll<T> ]
    PipelineContext semántico
    Bloque SafetyAsync en los elementos del route
```
![](https://github.com/vicboma1/ktor-API-examples/blob/master/00-assets/03-location-api.png)


#### [04 Async API | withContext](https://github.com/vicboma1/ktor-API-examples/tree/master/04-async-api)
```
    Exposición de una API enriquecida con los elementos operacionales del CRUD - number
    Operaciones: [ get, getAll, add, postMinor, postMajor, postEquals, delete, deleteAll ]
    Bloque SafetyAsyncWithContext en los elementos del route (no optimizado, es global a la operación)
    Retrocompatibilidad con proyecto [ 03 Locations API | type-safe Routing ]
```
![](https://github.com/vicboma1/ktor-API-examples/blob/master/00-assets/04-async-api.png)

#### [05 Flow API | Experimentos](https://github.com/vicboma1/ktor-API-examples/tree/master/05-flow-api)
```
    Exposición de una API enriquecida con elementos operacionales flowables y diferentes block-body
    Operaciones: [ getFlow, getFlowBlock, getFlowContext, getFlowOdd, getFlowBlockOdd, getFlowContextOdd,getFlowEven, getFlowBlockEven, getFlowContextEven ]
    Refactor en el routing con un solo entryPoint 
    Retrocompatibilidad con proyecto [ 04 Async API | withContext ]
```

#### [06 Image API | Monochroma](https://github.com/vicboma1/ktor-API-examples/tree/master/06-monochroma-api)
```
    Basado en el video 'Mobile Backends with Kotlin and Google Cloud (Google I/O'19)' pero alojando el recurso en Local
    Exposición de una API customizada para el tratamiento de imágenes
    Operacion: [ putColorMono] 
    Retrocompatibilidad con proyecto [ 05 Flow API | Experimentos ]
```
![](https://github.com/vicboma1/ktor-API-examples/blob/master/00-assets/06-monochrome-api.png)

![](https://github.com/vicboma1/ktor-API-examples/blob/master/00-assets/06-monochroma-api.gif)

#### [07 static Content API | Recursos](https://github.com/vicboma1/ktor-API-examples/tree/master/07-staticcontent-api)
```
    Reemplazo de 'install(DefaultHeader)' por 'install(AutoHeadResponse)'
    Definición de un remotePath
    Añadido paquete con recursos
    Retrocompatibilidad con proyecto [ 06 Image API | Monochroma ]
```
![](https://github.com/vicboma1/ktor-API-examples/blob/master/00-assets/06-monochrome-api.png)

#### [08 Basic Auth API | Acceso ](https://github.com/vicboma1/ktor-API-examples/tree/master/08-basiauth-api)
```
    Añadido install(Authentication)
    Añadida validación de acceso
    Exposición de una API customizada para el tratamiento de accesos con autenticacion
    Operacion: [ get ] 
    Retrocompatibilidad con proyecto [ 07 static Content API | Recursos ]
```
![](https://github.com/vicboma1/ktor-API-examples/blob/master/00-assets/08-basicAuth-api.gif)

### Referencias 
*  [Documentación oficial](https://ktor.io/)
*  [Mobile Backends with Kotlin and Google Cloud (Google I/O'19)](https://youtu.be/zjWOMBdPbsI)
*  [GOTO 2019 • Server-side Kotlin with Coroutines • Roman Elizarov](https://www.youtube.com/watch?v=hQrFfwT1IMo)
*  [Asynchronous-flow](https://github.com/Kotlin/kotlinx.coroutines/blob/7f0da424ed98e3a30e0a7ca2daff33f9a9cdbf0c/docs/flow.md#asynchronous-flow)
*  [LiveData with Coroutines and Flow (Android Dev Summit '19)](https://www.youtube.com/watch?v=B8ppnjGPAGE)
*  [Ktor-samples](https://github.com/ktorio/ktor-samples)
*  [Server-Side Development with Ktor by Hadi Hariri - Bengaluru, June 22, 2019](https://youtu.be/Y4kyTpi_qO4)
*  [Repo: Getting Started Kotlin](https://github.com/vicboma1/GettingStartedKotlin)    
*  [Repo: Problems Kotlin](https://github.com/vicboma1/Kotlin-Examples-Problems/blob/master/README.md)    
*  [Repo: GameBoy Emulator Enviroment](https://github.com/vicboma1/GameBoyEmulatorEnvironment)    
*  [Repo: Kotlin Mobile](https://github.com/vicboma1/KotlinMobilePoC_MasterUV2018)    
*  [Repo: Kotlin JavaScript](https://github.com/vicboma1/kotlinJavaScript)   
*  [Repo: Kotlin Native-iOS](https://github.com/vicboma1/Kotlin-Native-iOS-ConsoleAsync)   
*  [Repo: Kotlin Koans Examples](https://github.com/vicboma1/Kotlin-Koans)   
