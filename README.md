# ktor-API-examples

### Ejemplos con KTOR para la creación de un API REST


#### 01 Basic API | CRUD 
```
    Exposición de una API básica con los elementos operacionales del CRUD
    Operaciones: [ get, put, post, delete]
    PipelineContext semántico
    Bloque SafetyAsync en los elementos del route
```
![](https://github.com/vicboma1/ktor-API-examples/blob/master/00-assets/01-basic-api.png)


#### 02 Rich API | CRUD
```
    Refactors en el routing anclando el end-point base
    Refactors en el route sin rutas, solo parámetros de entrada
    Exposición de una API enriquecida con los elementos operacionales del CRUD
    Operaciones: [ get, put, post, delete, getAll, deleteAll]
    PipelineContext semántico
    Bloque SafetyAsync en los elementos del route
```
![](https://github.com/vicboma1/ktor-API-examples/blob/master/00-assets/02-rich-api.png)


#### 03 Locations API | type-safe Routing
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