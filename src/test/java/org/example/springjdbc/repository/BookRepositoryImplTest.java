package org.example.springjdbc.repository;

import org.example.springjdbc.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BookRepositoryImplTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //@Autowired
    @Autowired
    private BookRepositoryImpl bookRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    title VARCHAR(255) NOT NULL,\n" +
                "    author VARCHAR(255) NOT NULL,\n" +
                "    publication_year INT NOT NULL)");
    }

    @AfterEach
    void close() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS books");
    }

    @Test
    void testSaveAndFindById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Title");
        book.setAuthor("Test Author");
        book.setPublicationYear(2024);

        int result = bookRepository.save(book);
        assertEquals(1, result);

        Book foundBook = bookRepository.findById(1L).orElse(null);
        System.out.println(bookRepository.findAll());
        assertNotNull(foundBook);
        assertEquals("Test Title", foundBook.getTitle());
        assertEquals("Test Author", foundBook.getAuthor());
        assertEquals(2024, foundBook.getPublicationYear());
    }

    @Test
    void testFindAll() {
        Book book1 = new Book();
        book1.setTitle("Title 1");
        book1.setAuthor("Author 1");
        book1.setPublicationYear(2021);

        Book book2 = new Book();
        book2.setTitle("Title 2");
        book2.setAuthor("Author 2");
        book2.setPublicationYear(2022);

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
    }

    @Test
    void testUpdate() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Original Title");
        book.setAuthor("Original Author");
        book.setPublicationYear(2023);

        bookRepository.save(book);

        book.setTitle("Updated Title");
        book.setAuthor("Updated Author");

        int result = bookRepository.update(book);
        assertEquals(1, result);

        Book updatedBook = bookRepository.findById(1L).orElse(null);
        assertNotNull(updatedBook);
        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthor());
    }

    @Test
    void testDeleteById() {
        Book book = new Book();
        book.setTitle("To Be Deleted");
        book.setAuthor("To Be Deleted");
        book.setPublicationYear(2024);

        bookRepository.save(book);

        int result = bookRepository.deleteById(1L);
        assertEquals(1, result);

        Book deletedBook = bookRepository.findById(1L).orElse(null);
        assertNull(deletedBook);
    }

}