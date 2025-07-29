package com.mysite.sbb;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

//질문 데이터 저장
	@Test
	void testJpa() {
		Question q1 = new Question(); //Question 객체를 만들어 변수 q1에 저장
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);  // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);  // 두번째 질문 저장
	}

//질문 데이터 조회
	@Test
	void testJpa02 () {
		List<Question> all = this.questionRepository.findAll(); // question 데이터베이스에 있는 모든 Question 객체를 변수 all에 저장
		assertEquals(2, all.size()); //all 리스트의 사이즈가 2인지 확인

		Question q = all.get(0); //all 리스트에서 첫 번째 요소를 가져와 Question 변수 q에 저장
		assertEquals("sbb가 무엇인가요?", q.getSubject()); //변수 q의 Subject 중에 동일한 글이 있는지 확인
	}

//findById 메서드
	@Test
	void testJpa03 () {
		//question 데이터베이스에서 아이디 1을 가진 Question 객체가 있는지 확인하고 그 값을 변수 oq에 저장
		Optional<Question> oq = this.questionRepository.findById(1); //찾기를 할 때 있다, 없다를 표현할때 Optional 사용

		if(oq.isPresent()) { //확인 //oq 안에 값이 존재할 경우에만 아래 코드를 실행
			Question q = oq.get(); //oq의 값을 불러오는 변수 q를 생성
			assertEquals("sbb가 무엇인가요?", q.getSubject()); //q의 값 중 Subject의 값과 동일한지 확인
		}
	}

//findBySubject 메서드
	@Test
	void testJpa04 () {
		//question 데이터베이스에서 검색한 Subject와 일치하는 객체를 찾아 변수 q에 저장
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId()); //q의 아이디를 가져와 1인지 확인
	}

//findBySubjectAndContent 메서드
	@Test
	void testJpa05 () {
		//question 데이터베이스에서 검색한 Subject와 Content가 일치하는 객체를 찾아 변수 q에 저장
		Question q = this.questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, q.getId()); //q의 아이디를 가져와 1인지 확인
	}

//findBySubjectLike 메서드
	@Test
	void testJpa06 () {
		//question 데이터베이스에서 subject가 "sbb"로 시작하는 모든 Question 객체를 qList에 저장
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0); // qList에서 첫번째 객체를 사용하는 Question 변수 q를 생성
		assertEquals("sbb가 무엇인가요?", q.getSubject()); // q에서 subject를 가져와 글과 동일한지 확인
	}

//질문 데이터 수정
	@Test
	void testJpa07 () {
		// question 데이터베이스에서 아이디 1의 값을 가진 객체를 변수 oq에 저장
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent()); // oq에 값이 존재한다면 true 존재하지 않는다면 AssertionError 발생
		Question q = oq.get(); //oq의 값을 가져오는 변수 q를 생성(oq에 값이 있어야만 안전하게 실행됨, 위에서 검사를 진행하여 안전함)
		q.setSubject("수정된 제목"); //찾아온 질문의 제목을 "수정된 제목"으로 변경
		this.questionRepository.save(q); //q를 변경한 값을 데이터베이스에 저장
	}

//질문 데이터 삭제
	@Test
	void testJpa08 () {
		//question 데이터베이스의 값이 2개인지 확인
		assertEquals(2, this.questionRepository.count());
		//question 데이터 베이스에서 1번 아이디를 가진 Question 객체를 담은 변수 oq를 생성
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent()); // oq에 값이 있는지 확인
		Question q = oq.get(); // oq에서 값을 가져와 q에 저장
		this.questionRepository.delete(q); // 가져온 값 q를 삭제
		// question 데이터베이스에서 선택한 값이 잘 삭제되어 1개만 남았는지 확인
		assertEquals(1, this.questionRepository.count());
	}

//답변 데이터 저장
	@Test
	void testJpa09 () {
		//question 데이터 베이스에서 2번 아이디를 가진 Question 객체를 담은 변수 oq를 생성
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent()); // oq에 값이 있는지 확인
		Question q = oq.get(); // oq에서 값을 가져와 q에 저장

		Answer a = new Answer(); //Answer 라는 새로운 객체를 만들어 변수 a에 저장
		a.setContent("네 자동으로 생성됩니다."); // a의 Content에 해당 답변 저장
		a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다. //a의 Question에 q 저장
		a.setCreateDate(LocalDateTime.now()); //a의 CreateDate에 작성 시간 저장
		this.answerRepository.save(a); // 수정된 a를 answer 데이터베이스에 저장
	}

//답변 데이터 조회
	@Test
	void testJpa10 () {
		// answer 데이터베이스에서 아이디 1을 가진 answer 객체를 변수 oa에 저장
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent()); //oa에 값이 있는지 확인
		Answer a = oa.get(); // oa에서 값을 가져와 변수 a에 저장
		assertEquals(2, a.getQuestion().getId()); // a가 참조하고 있는 Question의 ID가 2인지 확인
	}

//답변 데이터를 통해 질문 데이터 찾기, 질문 데이터를 통해 답변 데이터 찾기
	@Transactional
	@Test
	void testJpa11 () {
		// question 데이터베이스에서 아이디 2를 가진 객체를 Optional로 감싸 변수 oq에 저장
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent()); //oq에 객체가 있는지 확인
		Question q = oq.get(); //oq에 저장된 객체를 변수 q에 저장

		List<Answer> answerList = q.getAnswerList(); //q에서 달린 답변, AnswerList를 가져와 리스트변수 answerList에 저장

		assertEquals(1, answerList.size()); //리스트변수 answerList의 크기가 1과 같은지 확인
		// 리스트변수 answerList의 첫번째 객체의 Content가 글과 동일한지 확인
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
}
