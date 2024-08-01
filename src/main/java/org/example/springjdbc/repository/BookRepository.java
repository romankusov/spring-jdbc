package org.example.springjdbc.repository;
import org.example.springjdbc.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    int save(Book book);
    int update(Book book);
    int deleteById(Long id);
}