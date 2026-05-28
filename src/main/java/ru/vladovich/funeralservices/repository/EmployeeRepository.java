package ru.vladovich.funeralservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vladovich.funeralservices.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
