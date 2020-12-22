package jpa.jpaservice.domainPakage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery
{
	@Id
	@GeneratedValue
	@Column(name = "delivery_id")
	private Long id;
	
	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // order 객체에 있는 delivery 변수와 mapping
	private Order order;
	
	@Embedded
	private Address address;
	
	// @Enumerated(EnumType.ORDINAL) // ordinal로 할 경우, 새로 enum을 추가하면 DB가 꼬인다... 그래서 안씀 젯따이
	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus; // READY, COMPLETED
	
	
}
