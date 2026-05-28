package ru.vladovich.funeralservices.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vladovich.funeralservices.entity.Client;
import ru.vladovich.funeralservices.exception.ResourceNotFoundException;
import ru.vladovich.funeralservices.repository.ClientRepository;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public Client update(Long id, Client client) {
        Client existingClient = findById(id);
        existingClient.setFullName(client.getFullName());
        existingClient.setPhone(client.getPhone());
        return clientRepository.save(existingClient);
    }

    public void delete(Long id) {
        Client client = findById(id);
        clientRepository.delete(client);
    }
}
