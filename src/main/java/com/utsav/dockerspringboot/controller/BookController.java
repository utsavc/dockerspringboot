package com.utsav.dockerspringboot.controller;

import com.utsav.dockerspringboot.model.Book;
import com.utsav.dockerspringboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;


    @PostMapping("/add")
    public ResponseEntity<Book> add(@RequestBody Book book) {
        Book save = bookService.save(book);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }
}
