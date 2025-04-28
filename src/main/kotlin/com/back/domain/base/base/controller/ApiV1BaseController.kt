package com.back.domain.base.base.controller

import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiV1BaseController(
    val embeddingModel: EmbeddingModel
) {
    @GetMapping("/api/v1/base/embed")
    @Cacheable(value = ["embedding"], key = "#text")
    fun embed(text: String): FloatArray {
        return embeddingModel.embed(text)
    }

    @PostMapping("/api/v1/base/embed")
    @Cacheable(value = ["embedding"], key = "#reqBody.text")
    fun embed(@RequestBody reqBody: BaseEmbedReqBody): FloatArray {
        return embeddingModel.embed(reqBody.text)
    }
}

data class BaseEmbedReqBody(
    val text: String
)