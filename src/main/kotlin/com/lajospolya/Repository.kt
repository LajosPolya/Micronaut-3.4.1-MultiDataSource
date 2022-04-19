package com.lajospolya

import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import io.micronaut.transaction.annotation.TransactionalAdvice
import io.micronaut.transaction.support.TransactionSynchronizationManager
import jakarta.inject.Named
import jakarta.inject.Singleton
import java.util.*
import javax.persistence.EntityManager

open class RepositoryProvider<T>(
    private val readOnlyRepo: T,
    private val readWriteRepo: T,
) {
    val repo: T
        get() {
            val txReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            return if (txReadOnly) readOnlyRepo else readWriteRepo
        }
}

@Singleton
open class ParentRepositoryProvider(
    readOnlyRepository: ParentReadOnlyRepository,
    readWriteRepository: ParentReadWriteRepository,
): RepositoryProvider<ParentRepository>(readOnlyRepository, readWriteRepository)

@Repository("readonly")
// @TransactionalAdvice("readonly")
abstract class ParentReadOnlyRepository(
    @Named("readonly") entityManager: EntityManager,
): ParentRepository(entityManager)


@Repository("default")
// @TransactionalAdvice("default")
abstract class ParentReadWriteRepository(
    @Named("default") entityManager: EntityManager,
): ParentRepository(entityManager)

abstract class ParentRepository(
    private val entityManager: EntityManager,
): PageableRepository<Parent, Int> {

    @Executable
    abstract override fun findById(id:Int): Optional<Parent>

    fun findByIdWithEntityManager(id: Int): Parent? {
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Parent::class.java)
        val entity = criteriaQuery.from(Parent::class.java)

        val predicate = criteriaBuilder.equal(entity.get<Int>("id"), id)

        criteriaQuery.select(entity).where(predicate)

        return entityManager.createQuery(criteriaQuery).singleResult
    }
}

