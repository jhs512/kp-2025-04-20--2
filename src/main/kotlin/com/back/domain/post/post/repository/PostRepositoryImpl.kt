package com.back.domain.post.post.repository

import com.back.domain.post.post.entity.Post
import com.back.domain.post.post.entity.QPost
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

class PostRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : PostRepositoryCustom {

    override fun findByKw(
        kw: String,
        pageable: Pageable
    ): Page<Post> {
        val builder = BooleanBuilder()

        if (kw.isNotBlank()) {
            applyKeywordFilter(kw, builder)
        }

        val postsQuery = createPostsQuery(builder)
        applySorting(pageable, postsQuery, kw)

        postsQuery.offset(pageable.offset).limit(pageable.pageSize.toLong())

        val totalQuery = createTotalQuery(builder)

        return PageableExecutionUtils.getPage(postsQuery.fetch(), pageable) { totalQuery.fetchOne()!! }
    }

    private fun applyKeywordFilter(kw: String, builder: BooleanBuilder) {
        val matchExpr1 = Expressions.booleanTemplate(
            "match({0}, {1})",
            QPost.post.title,
            kw
        )

        val matchExpr2 = Expressions.booleanTemplate(
            "match({0}, {1})",
            QPost.post.content,
            kw
        )

        builder.and(matchExpr1)
        builder.or(matchExpr2)
    }

    private fun createPostsQuery(builder: BooleanBuilder): JPAQuery<Post> {
        return jpaQueryFactory
            .select(QPost.post)
            .from(QPost.post)
            .where(builder)
    }

    private fun applySorting(pageable: Pageable, postsQuery: JPAQuery<Post>, kw: String) {
        for (o in pageable.sort) {
            if (o.property == "scores" && kw.isNotBlank()) {
                // 검색 키워드가 있을 때만 score 계산

                val titleScore = Expressions.numberTemplate(
                    Double::class.java,
                    "score({0}, {1})",
                    QPost.post.title,
                    kw
                )

                val contentScore = Expressions.numberTemplate(
                    Double::class.java,
                    "score({0}, {1})",
                    QPost.post.content,
                    kw
                )

                val totalScore = Expressions.numberTemplate(
                    Double::class.java,
                    "{0} + {1}",
                    titleScore,
                    contentScore
                )

                postsQuery.orderBy(
                    OrderSpecifier(
                        if (o.isAscending) Order.ASC else Order.DESC,
                        totalScore
                    )
                )
            } else {
                // 일반 필드 정렬
                val pathBuilder: PathBuilder<*> =
                    PathBuilder(QPost.post.type, QPost.post.metadata)

                postsQuery.orderBy(
                    OrderSpecifier(
                        if (o.isAscending) Order.ASC else Order.DESC,
                        pathBuilder[o.property] as Expression<Comparable<*>>
                    )
                )
            }
        }
    }

    private fun createTotalQuery(builder: BooleanBuilder): JPAQuery<Long> {
        return jpaQueryFactory
            .select(QPost.post.count())
            .from(QPost.post)
            .where(builder)
    }
}
