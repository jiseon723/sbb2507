package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer/*id*/> {
    Question findBySubject(String subject); // Question 데이터베이스에서 subject를 찾을 것
    Question findBySubjectAndContent(String subject, String content); //Question 데이터베이스에서 subject와 content를 찾을 것
    List<Question> findBySubjectLike(String subject); // Question 데이터베이스에서 subject를 찾아 수정할 것
}
