package jpa.jpaservice.servicePackage;


import jpa.jpaservice.domainPakage.Member;
import jpa.jpaservice.repositoryPackage.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService
{
	private final MemberRepository memberRepository;
	
/*	@Autowired
	public void setMemberRepository(MemberRepository memberRepository)
	{
		this.memberRepository = memberRepository;
	}*/
	
	@Autowired
	public MemberService(MemberRepository memberRepository)
	{
		this.memberRepository = memberRepository;
	}

	
	// 회원 가입
	@Transactional
	public Long join(Member member)
	{
		// 중복 회원 체크
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}
	
	// 중복 회원 체크
	private void validateDuplicateMember(Member member)
	{
		List<Member> findMembers = memberRepository.findByName(member.getName());
		
		if(!findMembers.isEmpty())
		{
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
	
	// 회원 전체 조회
	@Transactional(readOnly = true) // 조회만 하기 떄문에 최적화 가능
	public List<Member> findMembers()
	{
		return memberRepository.findAll();
	}
	
	@Transactional(readOnly = true) // 조회만 하기 떄문에 최적화 가능
	public Member findOne(Long memberId)
	{
		return memberRepository.findOne(memberId);
	}
}
