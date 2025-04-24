package com.back.global.initData

import com.back.domain.member.member.service.MemberService
import com.back.domain.post.post.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.transaction.annotation.Transactional

@Configuration
class NotProdInitData(
    val memberService: MemberService,
    val postService: PostService,
) {
    @Autowired
    @Lazy
    lateinit var self: NotProdInitData

    @Bean
    fun notProdInitDataApplicationRunner(): ApplicationRunner {
        return ApplicationRunner {
            self.work1()
            self.work2()
        }
    }

    @Transactional
    fun work1() {
        if (memberService.count() > 0) return

        memberService.join("user1", "1234")
    }

    @Transactional
    fun work2() {
        if (postService.count() > 0) return

        val member1 = memberService.findByUsername("user1")!!

        postService.write(member1, "봄날의 산책", "날씨가 좋아서 공원에서 산책을 했어요.")
        postService.write(member1, "고양이 일기", "우리집 고양이가 오늘도 귀엽게 울었어요.")
        postService.write(member1, "커피 한 잔의 여유", "아침에 마시는 따뜻한 라떼 한 잔은 정말 행복하네요.")
        postService.write(member1, "서울 여행기", "경복궁과 북촌 한옥마을을 다녀왔습니다.")
        postService.write(member1, "스터디 후기", "오늘은 알고리즘 문제를 풀면서 팀원들과 많이 소통했어요.")
        postService.write(member1, "운동 루틴 공유", "요즘 홈트에 빠졌어요. 하루 30분씩 스트레칭과 유산소!")
        postService.write(member1, "책 리뷰: 작은 습관의 힘", "작은 습관이 인생을 바꾸는 데 얼마나 중요한지 알게 됐어요.")
        postService.write(member1, "비 오는 날", "비가 와서 창밖을 보며 음악을 들었어요. 감성 가득한 하루.")
        postService.write(member1, "제주도 맛집 탐방", "흑돼지와 고등어회 정말 최고였어요. 또 가고 싶다!")
        postService.write(member1, "프론트엔드 개발 시작", "React를 배우기 시작했어요. 컴포넌트 개념이 흥미롭네요.")
    }
}