package de.simonsymhoven.skillbatzbackend.controller;

import de.simonsymhoven.skillbatzbackend.model.Book;
import de.simonsymhoven.skillbatzbackend.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class BooksController {
	@Autowired
	private BookRepository repository;

  @GetMapping("/books")
  public List<Book> getAllBooks() {
    List<Book> books = new ArrayList<>();
    repository.findAll().forEach(books::add);

    return books;
  }

  @GetMapping(value = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Book getBookById(@PathVariable("id") long id) {
    Book _book = repository.findById(id).orElseThrow(() -> new DataNotFoundException(id));

    return _book;
  }

  @PostMapping(value = "/books/add", produces = MediaType.APPLICATION_JSON_VALUE)
  public Book addBook(@RequestBody Book book) {
    Book _book = repository.save(new Book(book.getId(), book.getText()));
    return _book;
  }

  @DeleteMapping(value = "/books/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {
    Book _book = repository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
    repository.deleteById(id);
    return new ResponseEntity<>(_book, HttpStatus.OK);
  }

  @DeleteMapping(value = "/books/delete", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> deleteAllBooks() {
    repository.deleteAll();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(value = "/books/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
    Optional<Book> bookData = repository.findById(id);

    if (bookData.isPresent()) {
      Book _book = bookData.get();
      _book.setText(book.getText());
      return new ResponseEntity<>(repository.save(_book), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
