package com.back.domain.post.post.controller

import com.back.domain.post.post.entity.Post2Gram
import com.back.domain.post.post.repository.Post2GramRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class ApiV1PostController(
    private val post2GramRepository: Post2GramRepository
) {
    @GetMapping
    fun getItems(): Page<Post2Gram> {
        val page = 1
        val pageSize = 30
        val pageable: Pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("id")))
        return post2GramRepository.findByKw("트", pageable)
    }
}