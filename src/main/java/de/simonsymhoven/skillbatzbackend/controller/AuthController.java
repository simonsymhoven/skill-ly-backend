package de.simonsymhoven.skillbatzbackend.controller;

import de.simonsymhoven.skillbatzbackend.message.request.SignUpForm;
import de.simonsymhoven.skillbatzbackend.message.response.JwtResponse;
import de.simonsymhoven.skillbatzbackend.model.Employee;
import de.simonsymhoven.skillbatzbackend.model.Permission;
import de.simonsymhoven.skillbatzbackend.model.PermissionName;
import de.simonsymhoven.skillbatzbackend.repository.EmployeeRepository;
import de.simonsymhoven.skillbatzbackend.repository.RoleRepository;
import de.simonsymhoven.skillbatzbackend.security.jwt.JwtProvider;
import de.simonsymhoven.skillbatzbackend.security.services.EmployeePrinciple;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtProvider jwtProvider;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody Employee employee) {

    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        employee.getUsername(),
        employee.getPassword()
      )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    EmployeePrinciple userDetails = (EmployeePrinciple) authentication.getPrincipal();
    String jwt = jwtProvider.generateJwtToken(authentication);
    return ResponseEntity.ok(new JwtResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<Employee> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
    if(employeeRepository.existsByUsername(signUpRequest.getUsername())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    if(employeeRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Employee employee = new Employee(signUpRequest.getName(), signUpRequest.getUsername(),
      signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));


    Set<Permission> permissions = new HashSet<>();
    Permission userPermission = roleRepository.findByName(PermissionName.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Permission not find."));
    permissions.add(userPermission);

    employee.setPermissions(permissions);
    employeeRepository.save(employee);

    return new ResponseEntity<>(employee, HttpStatus.OK);
  }
}
