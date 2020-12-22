package jpa.jpaservice.domainPakage.repositoryPackage;

import jpa.jpaservice.domainPakage.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepository
{
	private final EntityManager em;
	
	public void save(Order order)
	{
		em.persist(order);
	}
	
	public Order findOne(Long id)
	{
		return em.find(Order.class, id);
	}
	
	
	
	
	//== 검색 기능 ==//
	public List<Order> findAll(OrderSearch orderSearch)
	{
		// 1. jpql을 문자로 작성해서 조건문을 걸고 하는 것은 굉장히 불편하다!
		// 2. JPA CRITERIA
		// 3. 따라서 Querydsl을 쓴다.
		// 그러나 여기선 1번을 쓰자
		//language=JPAQL
		String jpql = "select o From Order o join o.member m";
		boolean isFirstCondition = true;
		//주문 상태 검색
		if (orderSearch.getOrderStatus() != null) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " o.status = :status";
		}
		//회원 이름 검색
		if (StringUtils.hasText(orderSearch.getMemberName())) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " m.name like :name";
		}
		TypedQuery<Order> query = em.createQuery(jpql, Order.class)
										  .setMaxResults(1000); //최대 1000건
		if (orderSearch.getOrderStatus() != null) {
			query = query.setParameter("status", orderSearch.getOrderStatus());
		}
		if (StringUtils.hasText(orderSearch.getMemberName())) {
			query = query.setParameter("name", orderSearch.getMemberName());
		}
		return query.getResultList();
	}
}
