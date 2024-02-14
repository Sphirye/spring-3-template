package com.sphirye.shared.utils

import com.sphirye.shared.exception.exceptions.ResourceNotFoundException
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

abstract class BaseService<T : Identifiable<ID>, ID : Any>(
    protected val repository: JpaRepository<T, ID>
) {
    open fun create(entity: T): T {
        val modifiedEntity = beforeCreate(entity)
        return repository.save(modifiedEntity!!)
    }

    open fun update(id: ID, entity: T): T {
        _validateResourceExistsById(id)

        val modifiedEntity = beforeUpdate(id, entity)
        return repository.save(modifiedEntity!!)
    }

    open fun findById(id: ID): T {
        _validateResourceExistsById(id)
        return repository.getReferenceById(id!!)
    }

    open fun findAll(pageable: Pageable): Page<T> {
        return repository.findAll(pageable)
    }

    open fun findAll(entity: T?, pageable: Pageable): Page<T> {
        return if (entity != null) {
            return repository.findAll(Example.of(entity, ExampleMatcher.matchingAny()), pageable)
        } else {
            findAll(pageable)
        }
    }

    open fun existsById(id: ID): Boolean {
        return repository.existsById(id)
    }

    open fun deleteById(id: ID) {
        _validateResourceExistsById(id)

        beforeDelete(id)

        return repository.deleteById(id)
    }

    open fun beforeCreate(entity: T): T = entity

    open fun beforeUpdate(id: ID, entity: T): T {
        entity.id = id
        return entity
    }

    open fun beforeDelete(id: ID) { }

    private fun _validateResourceExistsById(id: ID) {
        if (!existsById(id)) {
            throw ResourceNotFoundException("Resource with id $id does not exists")
        }
    }
}