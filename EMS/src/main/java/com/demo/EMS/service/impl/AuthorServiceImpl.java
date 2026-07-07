package com.demo.EMS.service.impl;

import com.demo.EMS.dto.AuthorDTO;
import com.demo.EMS.entity.Author;
import com.demo.EMS.exceptions.ResourceNotFoundException;
import com.demo.EMS.repository.AuthorRepository;
import com.demo.EMS.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = modelMapper.map(authorDTO, Author.class);
        Author savedAuthor = authorRepository.save(author);
        return modelMapper.map(savedAuthor, AuthorDTO.class);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .toList();
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return modelMapper.map(author, AuthorDTO.class);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        author.setName(authorDTO.getName());
        author.setEmail(authorDTO.getEmail());
        Author updatedAuthor = authorRepository.save(author);
        return modelMapper.map(updatedAuthor, AuthorDTO.class);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author);
        return;
    }

    @Override
    public AuthorDTO findAuthorByName(String name) {
        Author author = authorRepository.findAuthorByName(name);
        if (author != null) {
            return modelMapper.map(author, AuthorDTO.class);
        }
        throw new ResourceNotFoundException("Author not found with name: " + name);
    }
}
