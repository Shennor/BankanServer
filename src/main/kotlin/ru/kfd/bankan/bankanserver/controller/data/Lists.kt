package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.web.bind.annotation.*

// TODO
@RestController
@RequestMapping("api/list/")
class Lists {
    @PostMapping("{id}")
    fun create(@PathVariable id: Int, @RequestBody list: Any): Nothing = TODO("Not Implemented")

    @GetMapping("info/{id}")
    fun readInfo(@PathVariable id: Int): Nothing = TODO("Not Implemented")

    @GetMapping("{id}")
    fun readListContent(@PathVariable id: Int): Nothing = TODO("Not Implemented")

    @PatchMapping("{id}/edit/{property}")
    fun updateListProperty(@PathVariable id: Int, @PathVariable property: String): Nothing = TODO("Not Implemented")

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Int): Nothing = TODO("Not Implemented")
}
