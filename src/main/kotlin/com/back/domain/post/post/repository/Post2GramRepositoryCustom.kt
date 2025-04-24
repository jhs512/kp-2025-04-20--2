package com.back.domain.post.post.repository;

import com.back.domain.post.post.entity.Post2Gram
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface Post2GramRepositoryCustom {
    fun findByKw(
        kw: String,
        pageable: Pageable
    ): Page<Post2Gram>
}