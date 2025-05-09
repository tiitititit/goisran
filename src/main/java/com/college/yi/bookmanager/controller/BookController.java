package com.college.yi.bookmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.college.yi.bookmanager.exception.BookNotFoundException;
import com.college.yi.bookmanager.model.Book;
import com.college.yi.bookmanager.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	
	private final BookService bookService;
	
	public BookController(BookService bookService) {
        this.bookService = bookService;
	}
	
	

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<String> handleBookNotFound(BookNotFoundException ex) {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	
@GetMapping
public List<Book> getBooks() {
	return bookService.getAllBooks();
	    }

@PostMapping
public ResponseEntity<Book> addBook(@RequestBody Book book) {
    // サービス層で書籍データを登録
    Book savedBook = bookService.addBook(book);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);  // 201 Created
	}

//書籍更新API
@PutMapping("/{id}")
public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
    // 書籍情報を更新
    Book updatedBook = bookService.updateBook(id, book);
    return ResponseEntity.status(HttpStatus.OK).body(updatedBook);  // 200 OK
	}

@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content を返す
public void deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
}

}

