package com.college.yi.bookmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.college.yi.bookmanager.entity.BookEntity;
import com.college.yi.bookmanager.model.Book;
import com.college.yi.bookmanager.repository.BookRepository;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        BookEntity entity1 = new BookEntity();
        entity1.setId(1L);
        entity1.setTitle("タイトル1");
        entity1.setAuthor("著者1");
        entity1.setPublisher("出版社1");
        entity1.setPublishedDate(LocalDate.now());
        entity1.setStock(3);

        BookEntity entity2 = new BookEntity();
        entity2.setId(2L);
        entity2.setTitle("タイトル2");
        entity2.setAuthor("著者2");
        entity2.setPublisher("出版社2");
        entity2.setPublishedDate(LocalDate.now());
        entity2.setStock(5);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
        assertEquals("タイトル1", books.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testAddBook() {
        Book inputBook = new Book(null, "新しい本", "著者X", "出版社X", LocalDate.now(), 2);

        doAnswer(invocation -> {
            BookEntity arg = invocation.getArgument(0);
            arg.setId(10L); // 模擬的にIDを設定
            return null;
        }).when(bookRepository).insert(any(BookEntity.class));

        Book result = bookService.addBook(inputBook);

        assertNotNull(result.getId());
        assertEquals("新しい本", result.getTitle());
        verify(bookRepository, times(1)).insert(any(BookEntity.class));
    }

    @Test
    void testUpdateBook_存在する場合() {
        BookEntity existing = new BookEntity();
        existing.setId(5L);
        existing.setTitle("古い本");
        existing.setAuthor("古い著者");
        existing.setPublisher("旧出版社");
        existing.setPublishedDate(LocalDate.of(2020, 1, 1));
        existing.setStock(1);

        when(bookRepository.findById(5L)).thenReturn(existing);

        Book updated = new Book(null, "新しいタイトル", "新しい著者", "新出版社", LocalDate.of(2023, 5, 5), 10);

        Book result = bookService.updateBook(5L, updated);

        assertEquals("新しいタイトル", result.getTitle());
        assertEquals(10, result.getStock());
        verify(bookRepository, times(1)).findById(5L);
        verify(bookRepository, times(1)).update(existing);
    }

    @Test
    void testUpdateBook_存在しない場合() {
        when(bookRepository.findById(99L)).thenReturn(null);

        Book updated = new Book(null, "ダミー", "だれか", "なし", LocalDate.now(), 1);

        Book result = bookService.updateBook(99L, updated);

        assertNull(result);
        verify(bookRepository, times(1)).findById(99L);
        verify(bookRepository, never()).update(any());
    }

    @Test
    void testDeleteBook_存在する場合() {
        BookEntity existing = new BookEntity();
        existing.setId(1L);
        existing.setTitle("削除する本");
        existing.setAuthor("著者");
        existing.setPublisher("出版社");
        existing.setPublishedDate(LocalDate.now());
        existing.setStock(2);

        when(bookRepository.findById(1L)).thenReturn(existing);

        boolean result = bookService.deleteBook(1L);

        assertTrue(result);
        verify(bookRepository).delete(1L);
    }

    @Test
    void testDeleteBook_存在しない場合() {
        when(bookRepository.findById(1L)).thenReturn(null);

        boolean result = bookService.deleteBook(1L);

        assertFalse(result);
        verify(bookRepository, never()).delete(anyLong());
    }
}
