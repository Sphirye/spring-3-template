package com.sphirye.shared.web.constraints

import com.sphirye.shared.web.annotation.EntityExists
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.TypedQuery
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl
import org.springframework.stereotype.Component

@Component
class EntityExistsConstraint: ConstraintValidator<EntityExists, String?> {

    @PersistenceContext
    private var _entityManager: EntityManager? = null

    override fun initialize(databaseConstraint: EntityExists) {}

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null || _entityManager == null) {
            return true
        }

        val contextImpl = context as ConstraintValidatorContextImpl
        val annotation = contextImpl.constraintDescriptor.annotation as EntityExists
        val entity = annotation.entityName
        val primaryKeyField = annotation.primaryKey

        if (!_entityExists(entity)) {
            return false
        }

        lateinit var query: TypedQuery<Long>

        try {
            query = _entityManager!!.createQuery(
                "SELECT COUNT(*) FROM $entity e WHERE e.$primaryKeyField = :primaryKey",
                Long::class.javaObjectType
            )
            query.setParameter("primaryKey", value)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val count = query.singleResult
        return count > 0
    }

    private fun _entityExists(entity: String): Boolean {
        val entities = _entityManager!!.metamodel.entities

        return entities.any { it.name == entity }
    }

}