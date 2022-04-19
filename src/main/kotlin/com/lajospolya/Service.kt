package com.lajospolya

import io.micronaut.transaction.annotation.TransactionalAdvice
import jakarta.inject.Singleton
import java.util.*
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Singleton
open class Service(
    private val parentRepositoryProvider: ParentRepositoryProvider,
) {

    @Transactional
    @TransactionalAdvice(readOnly = true)
    open fun readOnlyExecutable(id: Int): Int {
        val parent = parentRepositoryProvider.repo.findById(id)

        // Exception thrown when Lazy Loaded Child Entity is fetched
        parent.get().child[0]?.id

        return parent.get().id!!
    }

    @Transactional
    open fun notReadOnlyExecutable(id: Int): Int {
        val parent = parentRepositoryProvider.repo.findById(id)

        parent.get().child[0]?.id

        return parent.get().id!!
    }

    @Transactional
    @TransactionalAdvice(readOnly = true)
    open fun readOnlyEntityManager(id: Int): Int {
        val parent = parentRepositoryProvider.repo.findByIdWithEntityManager(id)

        parent?.child?.get(0)?.id

        return parent?.id!!
    }

    @Transactional
    open fun notReadOnlyEntityManager(id: Int): Int {
        val parent = parentRepositoryProvider.repo.findByIdWithEntityManager(id)

        parent?.child?.get(0)?.id

        return parent?.id!!
    }

    @Transactional
    open fun createEntity(): Int {
        val child = Child(UUID.randomUUID().toString())

        val parent = Parent(UUID.randomUUID().toString(), mutableListOf(child))
        child.parent = parent

        return parentRepositoryProvider.repo.save(parent).id!!
    }
}