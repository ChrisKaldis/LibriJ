package com.kaldis.chris.librij.data.jpa.repository;

import com.kaldis.chris.librij.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findAllByLocationId(UUID locationId);

}
