package ru.kfd.bankan.bankanserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankanServerApplication

fun main(args: Array<String>) {
    runApplication<BankanServerApplication>(*args)
}
