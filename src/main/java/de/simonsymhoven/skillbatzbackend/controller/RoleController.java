package de.simonsymhoven.skillbatzbackend.controller;

import de.simonsymhoven.skillbatzbackend.model.Permission;
import de.simonsymhoven.skillbatzbackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class RoleController {
  @Autowired
  private RoleRepository repository;

  @GetMapping("/roles")
  public List<Permission> getAllRoles() {
    return new ArrayList<>(repository.findAll());
  }
}
