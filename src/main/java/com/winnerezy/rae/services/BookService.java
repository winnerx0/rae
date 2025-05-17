package com.winnerezy.rae.services;

import com.winnerezy.rae.config.ImageConverter;
import com.winnerezy.rae.dto.BookDTO;
import com.winnerezy.rae.dto.EditBookDTO;
import com.winnerezy.rae.exceptions.ResourceNotFoundException;
import com.winnerezy.rae.models.Book;
import com.winnerezy.rae.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Optional<Book> getBook(String bookId){

        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isEmpty()){
            throw new ResourceNotFoundException("No book found with ID: " + bookId);
        }
        return book;
    }

    public Book editBook(String bookId, EditBookDTO editBookDTO) throws IOException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("No book found with ID: " + bookId));
        if (editBookDTO.title() != null) {
            book.setTitle(editBookDTO.title());
        }
        if (editBookDTO.description() != null) {
            book.setDescription(editBookDTO.description());
        }

        if(editBookDTO.image() != null){
            book.setImage(imageConverter.convertToBase64(editBookDTO.image()));
        }
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
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

}
