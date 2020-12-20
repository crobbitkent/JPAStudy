package jpa.jpaservice.domainPakage;


import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 내장될 것이다.
@Getter
public class Address
{
	private String city;
	private String street;
	private String zipcode;
	
	// JPA에서 리플렉션 같은 기술을 사용할 수 있도록 지원하기 위해서 생성자를 만들어둔다.
	protected Address()
	{
	
	}
	
	// 정보를 생성 시에만 저장하고 수정 불가
	public Address(String city, String street, String zipcode)
	{
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
}
