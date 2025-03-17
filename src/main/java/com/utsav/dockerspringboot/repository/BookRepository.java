package com.utsav.dockerspringboot.repository;

import com.utsav.dockerspringboot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
