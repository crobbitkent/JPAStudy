package jpa.jpaservice.domainPakage.servicePackage;

import jpa.jpaservice.domainPakage.Member;
import jpa.jpaservice.repositoryPackage.MemberRepository;

import jpa.jpaservice.servicePackage.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 롤백 역할. 테스트에서만
public class MemberServiceTest
{
	@Autowired
	MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em;
	
	@Test
	// @Rollback(false) // 롤백을 안하면 DB에 테스트 내용 확인 가능
	public void 회원가입() throws Exception
	{
		// given
		Member member = new Member();
		member.setName("kim");
		
		// when
		Long saveId = memberService.join(member);
		
		
		
		// then
		em.flush(); // 쿼리 등록 내용을 DB에 반영
		assertEquals(member, memberRepository.findOne(saveId));
		
	}
	
	@Test(expected = IllegalStateException.class)
	public void 중복_회원_예외() throws Exception
	{
		Member member1 = new Member();
		member1.setName("kim");
		
		Member member2 = new Member();
		member2.setName("kim");
		
		// when
		memberService.join(member1);
		memberService.join(member2); // 예외가 발생해야 한다.
		
		/*try {
			memberService.join(member2); // 예외가 발생해야 한다.
		} catch(IllegalStateException e) {
			return;
		}*/
	
		// then
		fail("예외가 발생해야 한다.");
	}
}