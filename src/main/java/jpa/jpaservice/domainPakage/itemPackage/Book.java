package jpa.jpaservice.domainPakage.itemPackage;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("Book")
public class Book extends Item
{
	private String author;
	private String isbn;
}
