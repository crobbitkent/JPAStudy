package jpa.jpaservice.domainPakage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member
{
	@Id	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	
	private String name;
	
	@Embedded // Embedable이나 Embedded 하나만 있으면 되지만 둘 다 있어도 좋다.
	private Address address;
	
	@OneToMany(mappedBy = "member") // order에 있는 member 변수에 의해 맵핑된 것이다. 즉, 읽기 전용이 된 것
	private List<Order> orders = new ArrayList<>();
}
