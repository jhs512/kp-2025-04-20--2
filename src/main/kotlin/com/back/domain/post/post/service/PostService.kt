package com.back.domain.post.post.service

import com.back.domain.member.member.entity.Member
import com.back.domain.post.post.entity.Post
import com.back.domain.post.post.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun count(): Long {
        return postRepository.count()
    }

    fun write(author: Member, title: String, content: String): Post {
        return postRepository.save(
            Post(
                author = author,
                title = title,
                content = content
            )
        )
    }

    fun findByKw(kw: String, pageable: Pageable): Page<Post> {
        return postRepository.findByKw(kw, pageable)
    }
}