package com.demo.EMS.repository;

import com.demo.EMS.entity.Author;
import com.demo.EMS.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Book book;

    private Author author;

    @BeforeEach
    void setUp(){
        author = Author.builder()
                .id(2L)
                .name("Guido van Rossum")
                .email("guido@gmail.com")
                .build();

        author = authorRepository.save(author); // Save the author entity first

        book = Book.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming Language")
                .publishedDate(LocalDate.parse("2026-05-25")) // Updated to current date
                .author(author)
                .build();
    }


    @Test
    void testFindBooksByAuthorId_whenAuthorIdIsPresent_thenReturnBooks(){
        //given
        bookRepository.save(book);

        //when
        List<Book> books = bookRepository.findBooksByAuthorId(2L);

        //then
        Assertions.assertFalse(books.isEmpty());
        Assertions.assertEquals(books.get(0).getTitle(), book.getTitle());
        Assertions.assertEquals(books.get(0).getId(), book.getId());
    }

    @Test
    void testFindBooksByAuthorId_whenAuthorIdIsNotFound_thenReturnEmptyList(){
        bookRepository.save(book);

        List<Book> books = bookRepository.findBooksByAuthorId(20L);

        Assertions.assertTrue(books.isEmpty());

    }


}
