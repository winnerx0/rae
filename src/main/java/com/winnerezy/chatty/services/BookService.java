package com.winnerezy.chatty.services;

import com.winnerezy.chatty.config.ImageConverter;
import com.winnerezy.chatty.dto.BookDTO;
import com.winnerezy.chatty.exceptions.ResourceNotFoundException;
import com.winnerezy.chatty.models.Book;
import com.winnerezy.chatty.repositories.BookRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserService userService;
    private final ImageConverter imageConverter;

    public BookService(BookRepository bookRepository, UserService userService, ImageConverter imageConverter){
        this.bookRepository = bookRepository;
        this.userService = userService;
        this.imageConverter = imageConverter;
    }

    public Book addBook(BookDTO bookDTO) throws IOException {
        Book book = new Book();
        book.setTitle(bookDTO.title());
        book.setDescription(bookDTO.description());
        String image = imageConverter.convertToBase64(bookDTO.image());
        book.setImage(image);
        book.setAuthor(userService.getCurrentUser());
        return bookRepository.save(book);
    }

    public Book editBook(String bookId, BookDTO bookDTO){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("No book found with ID: " + bookId));
        book.setTitle(bookDTO.title());
        book.setDescription(bookDTO.description());
        book.setUpdatedAt(LocalDate.now());
        return bookRepository.save(book);
    }

    public String deleteBook(String bookId){
        if(bookRepository.findById(bookId).isEmpty()){
            throw new ResourceNotFoundException("No book found with ID: " + bookId);
        }
        bookRepository.deleteById(bookId);
        return "Success";
    }

    public List<Book> searchBooks(String title){
        return bookRepository.searchByTitleIgnoreCase(title);
    }

}
