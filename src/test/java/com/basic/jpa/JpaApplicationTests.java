package com.basic.jpa;

import com.basic.jpa.entity.Member;
import com.basic.jpa.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class JpaApplicationTests {

	@Autowired
	MemberRepository memberRepository;

	@Test
	@DisplayName("Member 저장")
	void save() {
		for (int i = 0; i < 10; i++) {
			Member member = Member.builder()
								  .id(i)
								  .name("Test" + i)
								  .build();

			memberRepository.save(member);
		}
	}

	@Test
	@DisplayName("Member 조회")
	void find() {
		Optional<Member> member = memberRepository.findById(3);
		System.out.println("Member : " + member);
	}

	@Test
	@DisplayName("Member 삭제")
	void remove() {
		memberRepository.delete(Member.builder()
									  .id(5)
									  .name("Test5")
									  .build());
	}
}