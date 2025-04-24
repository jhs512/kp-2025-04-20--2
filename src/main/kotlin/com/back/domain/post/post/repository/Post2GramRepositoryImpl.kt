package com.back.domain.post.post.repository;

import com.back.domain.post.post.entity.Post2Gram
import com.back.domain.post.post.entity.QPost2Gram
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

class Post2GramRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : Post2GramRepositoryCustom {
    override fun findByKw(
        kw: String,
        pageable: Pageable
    ): Page<Post2Gram> {
        val builder = BooleanBuilder()

        if (kw.isNotBlank()) {
            applyKeywordFilter(kw, builder)
        }

        val postsQuery = createPostsQuery(builder, kw)
        applySorting(pageable, postsQuery)

        postsQuery.offset(pageable.offset).limit(pageable.pageSize.toLong())

        val totalQuery = createTotalQuery(builder)

        return PageableExecutionUtils.getPage(postsQuery.fetch(), pageable) { totalQuery.fetchOne()!! }
    }

    private fun applyKeywordFilter(kw: String, builder: BooleanBuilder) {
        applyFullTextMatch(kw, builder)
    }

    private fun createPostsQuery(builder: BooleanBuilder, kw: String): JPAQuery<Post2Gram> {
        val scoreExpr = Expressions.numberTemplate(
            Double::class.java,
            "score({0}, {1})",
            QPost2Gram.post2Gram.content,
            kw
        )
        
        return jpaQueryFactory
            .select(QPost2Gram.post2Gram)
            .from(QPost2Gram.post2Gram)
            .where(builder)
            .orderBy(scoreExpr.desc())
    }

    private fun applySorting(pageable: Pageable, postsQuery: JPAQuery<Post2Gram>) {
        for (o in pageable.sort) {
            val pathBuilder: PathBuilder<*> =
                PathBuilder<Any?>(QPost2Gram.post2Gram.type, QPost2Gram.post2Gram.metadata)

            postsQuery.orderBy(
                OrderSpecifier(
                    if (o.isAscending) Order.ASC else Order.DESC,
                    pathBuilder[o.property] as Expression<Comparable<*>>
                )
            )
        }
    }

    private fun createTotalQuery(builder: BooleanBuilder): JPAQuery<Long> {
        return jpaQueryFactory
            .select(QPost2Gram.post2Gram.count())
            .from(QPost2Gram.post2Gram)
            .where(builder)
    }

    private fun applyFullTextMatch(kw: String, builder: BooleanBuilder) {
        val matchExpr = Expressions.booleanTemplate(
            "match({0}, {1})",
            QPost2Gram.post2Gram.content,
            kw
        )
        builder.and(matchExpr)
    }
}
