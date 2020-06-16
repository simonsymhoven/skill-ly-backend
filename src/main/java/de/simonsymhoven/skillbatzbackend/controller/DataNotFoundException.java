package de.simonsymhoven.skillbatzbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND) // Or @ResponseStatus(HttpStatus.NO_CONTENT)
public class DataNotFoundException extends RuntimeException {
  DataNotFoundException(long id) {
    System.out.println("Data not found: " + id);
  }
  DataNotFoundException(String id) {
    System.out.println("Data not found: " + id);
  }
}
