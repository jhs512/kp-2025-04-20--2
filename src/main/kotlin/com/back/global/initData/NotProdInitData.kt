package com.back.global.initData

import com.back.domain.post.post.entity.Post2Gram
import com.back.domain.post.post.repository.Post2GramRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NotProdInitData(
    val post2GramRepository: Post2GramRepository,
) {
    @Bean
    fun notProdInitDataApplicationRunner(): ApplicationRunner {
        return ApplicationRunner {
            if (post2GramRepository.count() > 0) return@ApplicationRunner

            val posts = listOf(
                Post2Gram(title = "봄날의 산책", content = "날씨가 좋아서 공원에서 산책을 했어요."),
                Post2Gram(title = "고양이 일기", content = "우리집 고양이가 오늘도 귀엽게 울었어요."),
                Post2Gram(title = "커피 한 잔의 여유", content = "아침에 마시는 따뜻한 라떼 한 잔은 정말 행복하네요."),
                Post2Gram(title = "서울 여행기", content = "경복궁과 북촌 한옥마을을 다녀왔습니다."),
                Post2Gram(title = "스터디 후기", content = "오늘은 알고리즘 문제를 풀면서 팀원들과 많이 소통했어요."),
                Post2Gram(title = "운동 루틴 공유", content = "요즘 홈트에 빠졌어요. 하루 30분씩 스트레칭과 유산소!"),
                Post2Gram(title = "책 리뷰: 작은 습관의 힘", content = "작은 습관이 인생을 바꾸는 데 얼마나 중요한지 알게 됐어요."),
                Post2Gram(title = "비 오는 날", content = "비가 와서 창밖을 보며 음악을 들었어요. 감성 가득한 하루."),
                Post2Gram(title = "제주도 맛집 탐방", content = "흑돼지와 고등어회 정말 최고였어요. 또 가고 싶다!"),
                Post2Gram(title = "프론트엔드 개발 시작", content = "React를 배우기 시작했어요. 컴포넌트 개념이 흥미롭네요.")
            )

            post2GramRepository.saveAll(posts)
        }
    }
}