package com.back.domain.post.post.repository

import com.back.domain.post.post.entity.Post2Gram
import org.springframework.data.jpa.repository.JpaRepository

interface Post2GramRepository : JpaRepository<Post2Gram, Long>, Post2GramRepositoryCustom