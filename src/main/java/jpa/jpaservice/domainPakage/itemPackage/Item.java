package jpa.jpaservice.domainPakage.itemPackage;


import jpa.jpaservice.domainPakage.Category;
import jpa.jpaservice.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 전략
@DiscriminatorColumn(name = "dtype") // 이것에 따라 유형이 구분된다.
public abstract class Item
{
	@Id	@GeneratedValue
	@Column(name = "item_id")
	private Long id;
	

	
	private String name;
	
	private int price;

	private int stockQuantity;
	
	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();
	
	//== 비즈니스 로직 ==//
	// 재고 추가
	public void addStock(int quantity)
	{
		this.stockQuantity += quantity;
	}
	
	// 재고 감소
	public void removeStock(int quantity)
	{
		int rest = this.stockQuantity - quantity;
		
		if(0 > rest)
		{
			throw new NotEnoughStockException("need more stocks");
		}
		this.stockQuantity = rest;
	}
}
