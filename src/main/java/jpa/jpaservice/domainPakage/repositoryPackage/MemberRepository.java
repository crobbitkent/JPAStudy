package jpa.jpaservice.domainPakage.repositoryPackage;

import jpa.jpaservice.domainPakage.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository
{
	// 엔티티 매니저
	@PersistenceContext
	private final EntityManager em; // 스프링 부트가 제공(spring-boot-stater-data-jpa)
	
	public MemberRepository(EntityManager em)
	{
		this.em = em;
	}
	
	public Long save(Member member)
	{
		em.persist(member);
		return member.getId(); // 커맨드와 쿼리를 분리하기 위해서 멤버를 직접 리턴하지 않음
	}
	
	public Member findOne(Long id)
	{
		return em.find(Member.class, id);
	}
	
	public List<Member> findAll()
	{
		// JPQL을 적었다. 이것이 SQL로 자동번역된다.
		// 차이는 SQL은 테이블을 상대로 쿼리, JPQL은 객체를 상대로 쿼리
		List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
		
		return result;
	}
	
	public List<Member> findByName(String name)
	{
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
					   .setParameter("name", name).getResultList();
	}
}
