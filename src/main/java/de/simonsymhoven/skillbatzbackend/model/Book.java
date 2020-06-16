package de.simonsymhoven.skillbatzbackend.model;

import javax.persistence.*;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	String text;

	public Book() {}

  public Book(Long id, String text) {
    this.id = id;
    this.text = text;
  }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}



  public void setText(String text) {
		this.text = text;
	}
}
