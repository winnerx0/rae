package com.winnerezy.chatty.repositories;

import com.winnerezy.chatty.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query(value = "SELECT * FROM books WHERE title ILIKE %:title%", nativeQuery = true)
    List<Book> searchByTitleIgnoreCase(@Param("title") String title);
}
