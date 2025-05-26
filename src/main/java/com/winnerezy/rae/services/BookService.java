package com.winnerezy.rae.services;

import com.cloudinary.utils.ObjectUtils;
import com.winnerezy.rae.config.ImageConverter;
import com.winnerezy.rae.config.CloudinaryConfiguration;
import com.winnerezy.rae.dto.BookDTO;
import com.winnerezy.rae.dto.EditBookDTO;
import com.winnerezy.rae.exceptions.ResourceNotFoundException;
import com.winnerezy.rae.models.Book;
import com.winnerezy.rae.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserService userService;
    private final ImageConverter imageConverter;
    private final CloudinaryConfiguration cloudinaryConfiguration;

    public BookService(BookRepository bookRepository, UserService userService, ImageConverter imageConverter, CloudinaryConfiguration cloudinaryConfiguration){
        this.bookRepository = bookRepository;
        this.userService = userService;
        this.imageConverter = imageConverter;
        this.cloudinaryConfiguration = cloudinaryConfiguration;
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public Book addBook(BookDTO bookDTO) throws IOException {
        Book book = new Book();
        book.setTitle(bookDTO.title());
        book.setDescription(bookDTO.description());
        Map params = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
        );
        File file = File.createTempFile("rae", bookDTO.image().getOriginalFilename());

        bookDTO.image().transferTo(file);
        Object url = cloudinaryConfiguration.cloudinary().uploader().upload(file, params).get("secure_url");
        book.setImage(url.toString());
        book.setStars(bookDTO.stars());
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
        book.setUpdatedAt(LocalDateTime.now());
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
