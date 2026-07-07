package com.demo.EMS.repository;

import com.demo.EMS.dto.BookDTO;
import com.demo.EMS.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByAuthorId(Long authorId);
    Book findBookByTitle(String title);

    @Query(value = "SELECT * FROM books WHERE published_date > :publishingDate", nativeQuery = true)
    List<Book> findBooksAfterPublishedDate(String publishingDate);
}
