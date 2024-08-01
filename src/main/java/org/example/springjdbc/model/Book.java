package org.example.springjdbc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
}