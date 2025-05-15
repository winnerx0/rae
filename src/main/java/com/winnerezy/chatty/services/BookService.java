package com.winnerezy.chatty.services;

import com.winnerezy.chatty.config.ImageConverter;
import com.winnerezy.chatty.dto.BookDTO;
import com.winnerezy.chatty.models.Book;
import com.winnerezy.chatty.repositories.BookRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
        System.out.println(image);
        book.setImage(image);
        book.setAuthor(userService.getCurrentUser());
        return bookRepository.save(book);
    }


}
