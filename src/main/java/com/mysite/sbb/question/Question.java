package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동응답
    private Integer id;

    @Column(length = 200) // VARCHAR처럼 글의 길이를 제한
    private String subject;

    @Column(columnDefinition = "TEXT") // 텍스트 타입으로 긴글 저장.
    private String content;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER) // 주인을 지정, 질문을 삭제하면 안의 답변도 같이 삭제하겠다.
    private List<Answer> answerList;
}
