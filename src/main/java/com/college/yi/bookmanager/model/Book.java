package com.college.yi.bookmanager.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {
	private Long id; 
	private String title;
	private String author;//著者
	private String publisher;//出版社
	private LocalDate publishedDate;//出版日
	private int stock;//在庫数

public Book(Long id, String title, String author, String publisher, LocalDate publishedDate, int stock) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.publisher = publisher;
    this.publishedDate = publishedDate;
    this.stock = stock;
}

}
