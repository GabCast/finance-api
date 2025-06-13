package gabi.cast.finance_api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@OpenAPIDefinition(
	info = Info(
		title = "Finance API",
		version = "1.0",
		description = "API to track finances"
	)
)

@SpringBootApplication(
	//exclude = [DataSourceAutoConfiguration::class]
)
class FinanceApiApplication

fun main(args: Array<String>) {
	runApplication<FinanceApiApplication>(*args)
}
