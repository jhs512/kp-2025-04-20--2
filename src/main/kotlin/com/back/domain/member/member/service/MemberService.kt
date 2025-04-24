package com.back.domain.member.member.service

import com.back.domain.member.member.entity.Member
import com.back.domain.member.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun count(): Long {
        return memberRepository.count()
    }

    fun join(username: String, password: String): Member {
        return memberRepository.save(
            Member(username = username, password = password)
        )
    }

    fun findByUsername(username: String): Member? {
        return memberRepository.findByUsername(username)
    }
}