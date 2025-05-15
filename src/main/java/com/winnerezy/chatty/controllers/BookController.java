package com.winnerezy.chatty.controllers;

import com.winnerezy.chatty.dto.BookDTO;
import com.winnerezy.chatty.models.Book;
import com.winnerezy.chatty.repositories.BookRepository;
import com.winnerezy.chatty.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@Valid @ModelAttribute BookDTO bookDTO) throws IOException {
        return ResponseEntity.ok(bookService.addBook(bookDTO));

    }

}
