package com.back.domain.post.post.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    var _id: Long? = null,
    val title: String,
    @Column(columnDefinition = "LONGTEXT")
    val content: String,
) {
    val id: Long
        get() = _id ?: 0
}