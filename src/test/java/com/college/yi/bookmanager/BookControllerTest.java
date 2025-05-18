package com.college.yi.bookmanager;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.college.yi.bookmanager.controller.BookController;
import com.college.yi.bookmanager.model.Book;
import com.college.yi.bookmanager.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(controllers = BookController.class,
excludeAutoConfiguration = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})


public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private ObjectMapper objectMapper;
    private Book sampleBook;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); 

        // BookEntity は登場しないので、Book のみでOK
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("テストタイトル");
        sampleBook.setAuthor("テスト著者");
        sampleBook.setPublisher("テスト出版社");
        sampleBook.setPublishedDate(LocalDate.of(2024, 5, 1));
        sampleBook.setStock(10);
    }

    @Test
    void testGetBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(sampleBook));

        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("テストタイトル"))
            .andExpect(jsonPath("$[0].stock").value(10));
        
        

    }

    @Test
    void testAddBook() throws Exception {
        Book newBook = new Book();
        newBook.setTitle("新しい本");
        newBook.setAuthor("著者");
        newBook.setPublisher("出版社");
        newBook.setPublishedDate(LocalDate.of(2023, 1, 1));
        newBook.setStock(5);

        Book savedBook = new Book();
        savedBook.setId(2L);
        savedBook.setTitle("新しい本");
        savedBook.setAuthor("著者");
        savedBook.setPublisher("出版社");
        savedBook.setPublishedDate(LocalDate.of(2023, 1, 1));
        savedBook.setStock(5);

        when(bookService.addBook(any(Book.class))).thenReturn(savedBook);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.title").value("新しい本"));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("更新後タイトル");
        updatedBook.setAuthor("更新後著者");
        updatedBook.setPublisher("更新後出版社");
        updatedBook.setPublishedDate(LocalDate.of(2025, 1, 1));
        updatedBook.setStock(8);

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("更新後タイトル"))
            .andExpect(jsonPath("$.stock").value(8));
    }

    @Test
    void testDeleteBook() throws Exception {
        // deleteBook が boolean を返すならこれ
        when(bookService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1"))
               .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(1L);
    }

}