package jpa.jpaservice.domainPakage.servicePackage;

import jpa.jpaservice.domainPakage.Address;
import jpa.jpaservice.domainPakage.Member;
import jpa.jpaservice.domainPakage.Order;
import jpa.jpaservice.domainPakage.OrderStatus;
import jpa.jpaservice.itemPackage.Book;
import jpa.jpaservice.itemPackage.Item;
import jpa.jpaservice.repositoryPackage.OrderRepository;
import jpa.jpaservice.exception.NotEnoughStockException;
import jpa.jpaservice.servicePackage.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest
{
	@Autowired
	EntityManager em;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orderRepository;
	
	int orderCount = 3;
	int price = 15000;
	int quant = 10;
	
	@Test
	public void 상품주문() throws Exception
	{
		// given
		Member member = createMember();
		Item book = createBook(price, quant);
		
		// when
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		
		// then
		Order order = orderRepository.findOne(orderId);
		// "상품 주문 시 상태는 ORDER 인지 확인
		Assertions.assertEquals(OrderStatus.ORDER, order.getOrderStatus());
		// 주문한 상품 종류 수가 정확한지
		Assertions.assertEquals(1, order.getOrderItems().size());
		// 총 가격
		Assertions.assertEquals(price * orderCount, order.getTotalPrice());
		// 주문 수량만큼 재고가 줄어들었는지
		Assertions.assertEquals(quant - orderCount, book.getStockQuantity());
		
	}
	
	@Test
	public void 주문취소() throws Exception
	{
		// given
		Member member = createMember();
		Item book = createBook(price, quant);
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		
		
		// when
		orderService.cancelOrder(orderId);
		// then
		Order order = orderRepository.findOne(orderId);
		
		// 주문 취소시 상태는 CANCEL
		Assertions.assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
		// 주문이 취소된 상품은 재고가 증가해야한다.
		Assertions.assertEquals(quant, book.getStockQuantity());
	}
	
	@Test // (expected = NotEnoughStockException.class)
	public void 상품주문_재고수량초과() throws Exception
	{
		// given
		Member member = createMember();
		Item book = createBook(price, quant);
		
		// 총 재고에서 5개를 추가한 만큼 주문!
		int overOrderCount = quant + 5;
		
		// when

		
		// then
		try {
			orderService.order(member.getId(), book.getId(), overOrderCount);
		} catch(NotEnoughStockException e) {
			return;
		}
		
		fail("재고 수량 부족 예외가 발생해야 한다.");
	}
	//-----------------------------------------------------------------------------//
	private Member createMember()
	{
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "강남", "123123"));
		em.persist(member);
		
		return member;
	}
	
	private Item createBook(int price, int quant)
	{
		Item book = new Book();
		book.setName("JAP 완전정복");
		book.setPrice(price);
		book.setStockQuantity(quant);
		em.persist(book);
		
		return book;
	}
}