package ru.vladovich.funeralservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vladovich.funeralservices.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
