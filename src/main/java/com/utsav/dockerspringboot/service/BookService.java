package com.utsav.dockerspringboot.service;

import com.utsav.dockerspringboot.model.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);
    List<Book> findAll();
    Book findById(Long id);
}
