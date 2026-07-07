package com.demo.EMS.service;

import com.demo.EMS.dto.BookDTO;

import java.util.List;

public interface BookService {

    BookDTO createBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    void deleteBook(Long id);

    List<BookDTO> getBooksByAuthorId(Long authorId);
    BookDTO findBookByTitle(String title);
    List<BookDTO> findBooksAfterPublishedDate(String publishingDate);

}
