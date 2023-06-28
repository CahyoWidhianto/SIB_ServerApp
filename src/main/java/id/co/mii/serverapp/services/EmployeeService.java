package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.repositories.EmployeeRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class EmployeeService {

  private EmployeeRepository employeeRepository;

  public List<Employee> getAll() {
    return employeeRepository.findAll();
  }

  public Employee getById(Integer id) {
    return employeeRepository
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Employee not found!!!"
        )
      );
  }

  public Employee create(Employee employee) {
    return employeeRepository.save(employee);
  }

  public Employee update(Integer id, Employee employee) {
    getById(id);
    employee.setId(id);
    return employeeRepository.save(employee);
  }

  public Employee delete(Integer id) {
    Employee employee = getById(id);
    employeeRepository.delete(employee);
    return employee;
  }
}