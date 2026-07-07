package com.demo.EMS.service.impl;

import com.demo.EMS.dto.BookDTO;
import com.demo.EMS.entity.Author;
import com.demo.EMS.entity.Book;
import com.demo.EMS.exceptions.ResourceNotFoundException;
import com.demo.EMS.repository.AuthorRepository;
import com.demo.EMS.repository.BookRepository;
import com.demo.EMS.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookingRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        Author author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookDTO.getAuthorId()));
        book.setAuthor(author);
        Book savedBook = bookingRepository.save(book);
        return modelMapper.map(savedBook, BookDTO.class);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookingRepository.findAll();
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        Author author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookDTO.getAuthorId()));

        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setAuthor(author);

        Book updatedBook = bookingRepository.save(book);
        return modelMapper.map(updatedBook, BookDTO.class);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        bookingRepository.delete(book);
        return;
    }

    @Override
    public List<BookDTO> getBooksByAuthorId(Long authorId) {
        List<Book> books = bookingRepository.findBooksByAuthorId(authorId);
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }

    @Override
    public BookDTO findBookByTitle(String title) {
        Book book = bookingRepository.findBookByTitle(title);
        if (book != null) {
            return modelMapper.map(book, BookDTO.class);
        }
        throw new ResourceNotFoundException("Book not found with title : "+title);
    }

    @Override
    public List<BookDTO> findBooksAfterPublishedDate(String publishingDate) {
        List<Book> books = bookingRepository.findBooksAfterPublishedDate(publishingDate);
        return books.stream().map(book -> modelMapper.map(book, BookDTO.class)).toList();
    }
}
