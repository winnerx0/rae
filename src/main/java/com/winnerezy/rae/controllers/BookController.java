package com.winnerezy.rae.controllers;

import com.winnerezy.rae.dto.BookDTO;
import com.winnerezy.rae.dto.EditBookDTO;
import com.winnerezy.rae.models.Book;
import com.winnerezy.rae.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
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
