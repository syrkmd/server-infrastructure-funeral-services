package ru.vladovich.funeralservices.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vladovich.funeralservices.dto.FuneralOrderRequest;
import ru.vladovich.funeralservices.entity.FuneralOrder;
import ru.vladovich.funeralservices.service.FuneralOrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class FuneralOrderController {

    private final FuneralOrderService funeralOrderService;

    @GetMapping
    public List<FuneralOrder> getAllOrders(@RequestParam(required = false) Long clientId) {
        if (clientId != null) {
            return funeralOrderService.findByClient(clientId);
        }
        return funeralOrderService.findAll();
    }

    @GetMapping("/{id}")
    public FuneralOrder getOrderById(@PathVariable Long id) {
        return funeralOrderService.findById(id);
    }

    @PostMapping
    public ResponseEntity<FuneralOrder> createOrder(@Valid @RequestBody FuneralOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(funeralOrderService.create(request));
    }

    @PutMapping("/{id}")
    public FuneralOrder updateOrder(@PathVariable Long id, @Valid @RequestBody FuneralOrderRequest request) {
        return funeralOrderService.update(id, request);
    }

    @PutMapping("/{orderId}/assign-employee/{employeeId}")
    public FuneralOrder assignEmployee(@PathVariable Long orderId, @PathVariable Long employeeId) {
        return funeralOrderService.assignEmployee(orderId, employeeId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        funeralOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
