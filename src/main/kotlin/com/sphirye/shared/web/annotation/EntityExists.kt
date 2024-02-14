package com.sphirye.shared.web.annotation

import com.sphirye.shared.web.constraints.EntityExistsConstraint
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EntityExistsConstraint::class])
annotation class EntityExists(
    val message: String = "Reference does not exists",
    val entityName: String,
    val primaryKey: String,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)