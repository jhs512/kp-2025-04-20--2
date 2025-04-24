package com.back.domain.post.post.repository;

import com.back.domain.post.post.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostRepositoryCustom {
    fun findByKw(
        kw: String,
        pageable: Pageable
    ): Page<Post>
}