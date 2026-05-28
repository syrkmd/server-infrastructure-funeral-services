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
import ru.vladovich.funeralservices.dto.CeremonyScheduleRequest;
import ru.vladovich.funeralservices.entity.CeremonySchedule;
import ru.vladovich.funeralservices.service.CeremonyScheduleService;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class CeremonyScheduleController {

    private final CeremonyScheduleService ceremonyScheduleService;

    @GetMapping
    public List<CeremonySchedule> getAllSchedules(@RequestParam(required = false) Long employeeId) {
        if (employeeId != null) {
            return ceremonyScheduleService.findByEmployee(employeeId);
        }
        return ceremonyScheduleService.findAll();
    }

    @GetMapping("/{id}")
    public CeremonySchedule getScheduleById(@PathVariable Long id) {
        return ceremonyScheduleService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CeremonySchedule> createSchedule(@Valid @RequestBody CeremonyScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ceremonyScheduleService.create(request));
    }

    @PutMapping("/{id}")
    public CeremonySchedule updateSchedule(@PathVariable Long id, @Valid @RequestBody CeremonyScheduleRequest request) {
        return ceremonyScheduleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        ceremonyScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
