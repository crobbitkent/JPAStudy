package jpa.jpaservice.domainPakage;


import jpa.jpaservice.itemPackage.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category
{
	@Id	@GeneratedValue
	@Column(name = "category_id")
	private Long id;
	
	private String name;
	
	@ManyToMany
	@JoinTable(name = "category_item" // 중간 테이블을 연결, '다vs다'를 '일vs다'로 풀어내기 위해서
	// 그러나 ManyToMany를 실무에서 안쓴다. 필드를 추가하는게 불가능하기 때문
					  , joinColumns = @JoinColumn(name = "category_id")
					  , inverseJoinColumns = @JoinColumn(name = "item_id"))
	private List<Item> items = new ArrayList<>();
	
	// 카테고리 구조...
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Category> child = new ArrayList<>();
	
	
	//== 연관관계 편의 메서드 ==//
	public void addChildCategory(Category child)
	{
		this.child.add(child);
		child.setParent(this);
	}
}
