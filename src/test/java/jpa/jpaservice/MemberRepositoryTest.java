package jpa.jpaservice;

import jpa.jpaservice.domainPakage.Member;
import jpa.jpaservice.domainPakage.repositoryPackage.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest
{
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	@Transactional // test에 이게 있으면 테스트가 완료된후 DB를 rollback한다.
	@Rollback(false)
	public void testMember() throws Exception
	{
		// given
		Member member = new Member();
		member.setName("memberA");
		
		// when
		Long saveId = memberRepository.save(member);
		Member findMember = memberRepository.findOne(saveId);
		
		// then
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
		
		Assertions.assertThat(findMember).isEqualTo(member);
		// l> findMember == member...
	}
}