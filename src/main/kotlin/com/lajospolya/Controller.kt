package com.lajospolya

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post

@Controller
class Controller(
    private val service: Service,
) {

    @Get("/readOnlyExecutable/{id}")
    fun readOnlyExecutable(@PathVariable id: Int): ResponseDto {
        val parentId = service.readOnlyExecutable(id)

        return ResponseDto(parentId)
    }

    @Get("/notReadOnlyExecutable/{id}")
    fun notReadOnlyExecutable(@PathVariable id: Int): ResponseDto {
        val parentId = service.notReadOnlyExecutable(id)

        return ResponseDto(parentId)
    }

    @Get("/readOnlyEntityManager/{id}")
    fun readOnlyEntityManager(@PathVariable id: Int): ResponseDto {
        val parentId = service.readOnlyEntityManager(id)

        return ResponseDto(parentId)
    }

    @Get("/notReadOnlyEntityManager/{id}")
    fun notReadOnlyEntityManager(@PathVariable id: Int): ResponseDto {
        val parentId = service.notReadOnlyEntityManager(id)

        return ResponseDto(parentId)
    }

    @Post("/entity")
    fun createEntity(): ResponseDto {
        val parentId = service.createEntity()

        return ResponseDto(parentId)
    }
}