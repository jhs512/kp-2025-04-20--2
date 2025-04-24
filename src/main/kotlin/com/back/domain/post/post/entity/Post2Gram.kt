package com.back.domain.post.post.entity

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "post_2gram")
class Post2Gram(
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