package jpa.jpaservice.repositoryPackage;

import jpa.jpaservice.itemPackage.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository
{
	private final EntityManager em;
	
	public void save(Item item)
	{
		if(null == item.getId())
		{
			// 새로 생성해서 신규 등록
			em.persist(item);
		}
		else
		{
			// 기존 정보를 업데이트
			em.merge(item);
		}
	}
	
	// 아이템 하나 조회
	public Item findOne(Long id)
	{
		return em.find(Item.class, id);
	}
	
	public List<Item> findAll()
	{
		return em.createQuery("select i from Item i", Item.class).getResultList();
	}
}
