package de.simonsymhoven.skillbatzbackend.security.services;

import de.simonsymhoven.skillbatzbackend.model.Employee;
import de.simonsymhoven.skillbatzbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeDetailsServiceImpl implements UserDetailsService {
  @Autowired
  EmployeeRepository employeeRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {

    Employee employee = employeeRepository.findByUsername(username)
      .orElseThrow(() ->
        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
      );

    return EmployeePrinciple.build(employee);
  }
}
