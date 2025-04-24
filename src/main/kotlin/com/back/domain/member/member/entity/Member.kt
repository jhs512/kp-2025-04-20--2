package com.back.domain.member.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    var _id: Long? = null,
    @Column(unique = true)
    val username: String,
    val password: String,
) {
    val id: Long
        get() = _id ?: 0
}