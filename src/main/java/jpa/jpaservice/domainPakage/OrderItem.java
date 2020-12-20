package jpa.jpaservice.domainPakage;

import jpa.jpaservice.domainPakage.itemPackage.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// 주문 상품
// 주문 객체와 연관관계. 관계의 주인
@Entity
@Getter
@Setter
public class OrderItem
{
	@Id
	@GeneratedValue
	@Column(name = "order_item_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	private int orderPrice; // 주문 가격
	
	private int count; // 주문 수량
}
