package com.utsav.dockerspringboot.service;

import com.utsav.dockerspringboot.model.Book;
import com.utsav.dockerspringboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        if(bookRepository.findById(id).isPresent()){
            return bookRepository.findById(id).get();
        }else {
            throw new RuntimeException("Book not found");
        }
    }
}
