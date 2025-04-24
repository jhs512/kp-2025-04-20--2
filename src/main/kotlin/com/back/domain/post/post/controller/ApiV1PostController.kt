package com.back.domain.post.post.controller

import com.back.domain.post.post.entity.Post
import com.back.domain.post.post.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class ApiV1PostController(
    private val postService: PostService
) {
    @GetMapping
    @Transactional(readOnly = true)
    fun getItems(
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "size", defaultValue = "30") pageSize: Int,
        @RequestParam(value = "kw", defaultValue = "") kw: String
    ): Page<Post> {
        val pageable: Pageable =
            PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("score"), Sort.Order.desc("id")))

        return postService.findByKw(kw, pageable)
    }
}