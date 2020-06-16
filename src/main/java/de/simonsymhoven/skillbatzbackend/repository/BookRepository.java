package de.simonsymhoven.skillbatzbackend.repository;

import de.simonsymhoven.skillbatzbackend.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> { }
