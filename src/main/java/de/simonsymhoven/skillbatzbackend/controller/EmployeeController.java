package de.simonsymhoven.skillbatzbackend.controller;

import de.simonsymhoven.skillbatzbackend.model.Employee;
import de.simonsymhoven.skillbatzbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class EmployeeController {
    @Autowired
    private EmployeeRepository repository;

    @GetMapping(value = "/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getBookById(@PathVariable("id") String id) {
        Employee _employee = repository.findByUsername(id).orElseThrow(() -> new DataNotFoundException(id));

        return _employee;
    }
}
