package com.ryanpmartz.kupload

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KuploadApplication

fun main(args: Array<String>) {
    SpringApplication.run(KuploadApplication::class.java, *args)
}
