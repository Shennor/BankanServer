package ru.kfd.bankan.bankanserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Repository

@SpringBootApplication
class BankanServerApplication

fun main(args: Array<String>) {
    runApplication<BankanServerApplication>(*args)
}
