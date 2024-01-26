package org.dongguk.lavieenrosehotel

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import java.util.*

@EnableAsync
@SpringBootApplication
class LavieenrosehotelApplication {

	@PostConstruct
	fun started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
	}
}

fun main(args: Array<String>) {
	runApplication<LavieenrosehotelApplication>(*args)
}