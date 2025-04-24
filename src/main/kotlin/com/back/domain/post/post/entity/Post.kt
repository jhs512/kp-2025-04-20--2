package com.back.domain.post.post.entity

import com.back.domain.member.member.entity.Member
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    var _id: Long? = null,
    @Column(nullable = false)
    val title: String,
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    val content: String,
    @ManyToOne
    val author: Member,
) {
    val id: Long
        get() = _id ?: 0
}