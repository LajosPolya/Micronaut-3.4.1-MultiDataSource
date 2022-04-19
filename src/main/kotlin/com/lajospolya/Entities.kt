package com.lajospolya

import javax.persistence.*

@Entity
@Table(name = "parent")
open class Parent(
    @Column(nullable = true)
    var name: String?,

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        mappedBy = "parent"
    )
    var child: MutableList<Child?>,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_id", nullable = false)
    var id: Int? = null
}

@Entity
@Table(name = "child")
open class Child(
    @Column(nullable = true)
    var name: String?,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false, updatable = false)
    var parent: Parent? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id", nullable = false)
    var id: Int? = null
}