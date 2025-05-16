package com.winnerezy.chatty.controllers;

import com.winnerezy.chatty.dto.BookDTO;
import com.winnerezy.chatty.dto.EditBookDTO;
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
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> findBook(@PathVariable("id") String id) {
        Optional<Book> book = bookService.getBook(id);
        return ResponseEntity.ok(book);

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> editBook(@PathVariable("id") String id, @Valid @ModelAttribute EditBookDTO editBookDTO) throws IOException {
        return ResponseEntity.ok(bookService.editBook(id, editBookDTO));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") String id) {
        String response = bookService.deleteBook(id);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String title){
        return ResponseEntity.ok(bookService.searchBooks(title));
    }

}
