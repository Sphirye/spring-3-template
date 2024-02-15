package com.sphirye.springtemplate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan("com.sphirye")
@EnableJpaRepositories(includeFilters = [ComponentScan.Filter(Repository::class)])
class Spring3TemplateApplication

fun main(args: Array<String>) {
	runApplication<Spring3TemplateApplication>(*args)
}
