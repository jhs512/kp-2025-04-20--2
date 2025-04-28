package com.back.domain.post.post.dto

import com.back.domain.post.post.entity.Post

data class PostDto(
    val id: Long,
    val title: String,
    val content: String,
    val authorId: Long,
    val authorName: String,
) {
    constructor(post: Post) : this(
        id = post.id,
        title = post.title,
        content = post.content,
        authorId = post.author.id,
        authorName = post.author.username
    )
}