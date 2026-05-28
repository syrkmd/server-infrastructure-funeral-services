package ru.vladovich.funeralservices.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vladovich.funeralservices.entity.Employee;
import ru.vladovich.funeralservices.exception.ResourceNotFoundException;
import ru.vladovich.funeralservices.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Long id, Employee employee) {
        Employee existingEmployee = findById(id);
        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.setPosition(employee.getPosition());
        return employeeRepository.save(existingEmployee);
    }

    public void delete(Long id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
    }
}
