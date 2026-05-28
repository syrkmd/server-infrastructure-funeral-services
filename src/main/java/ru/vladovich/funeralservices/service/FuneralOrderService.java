package ru.vladovich.funeralservices.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vladovich.funeralservices.dto.FuneralOrderRequest;
import ru.vladovich.funeralservices.entity.Client;
import ru.vladovich.funeralservices.entity.Employee;
import ru.vladovich.funeralservices.entity.FuneralOrder;
import ru.vladovich.funeralservices.exception.ResourceNotFoundException;
import ru.vladovich.funeralservices.repository.ClientRepository;
import ru.vladovich.funeralservices.repository.EmployeeRepository;
import ru.vladovich.funeralservices.repository.FuneralOrderRepository;

@Service
@RequiredArgsConstructor
public class FuneralOrderService {

    private final FuneralOrderRepository funeralOrderRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    public List<FuneralOrder> findAll() {
        return funeralOrderRepository.findAll();
    }

    public List<FuneralOrder> findByClient(Long clientId) {
        return funeralOrderRepository.findByClientId(clientId);
    }

    public FuneralOrder findById(Long id) {
        return funeralOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funeral order not found with id: " + id));
    }

    public FuneralOrder create(FuneralOrderRequest request) {
        FuneralOrder funeralOrder = new FuneralOrder();
        fillFuneralOrder(funeralOrder, request);
        return funeralOrderRepository.save(funeralOrder);
    }

    public FuneralOrder update(Long id, FuneralOrderRequest request) {
        FuneralOrder funeralOrder = findById(id);
        fillFuneralOrder(funeralOrder, request);
        return funeralOrderRepository.save(funeralOrder);
    }

    public FuneralOrder assignEmployee(Long orderId, Long employeeId) {
        FuneralOrder funeralOrder = findById(orderId);
        Employee employee = findEmployee(employeeId);
        funeralOrder.setEmployee(employee);
        return funeralOrderRepository.save(funeralOrder);
    }

    public void delete(Long id) {
        FuneralOrder funeralOrder = findById(id);
        funeralOrderRepository.delete(funeralOrder);
    }

    private void fillFuneralOrder(FuneralOrder funeralOrder, FuneralOrderRequest request) {
        funeralOrder.setServiceName(request.getServiceName());
        funeralOrder.setCeremonyDate(request.getCeremonyDate());
        funeralOrder.setStatus(request.getStatus());
        funeralOrder.setClient(findClient(request.getClientId()));
        funeralOrder.setEmployee(request.getEmployeeId() == null ? null : findEmployee(request.getEmployeeId()));
    }

    private Client findClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    private Employee findEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }
}
