package ru.vladovich.funeralservices.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vladovich.funeralservices.dto.CeremonyScheduleRequest;
import ru.vladovich.funeralservices.entity.CeremonySchedule;
import ru.vladovich.funeralservices.entity.Employee;
import ru.vladovich.funeralservices.entity.FuneralOrder;
import ru.vladovich.funeralservices.exception.ResourceNotFoundException;
import ru.vladovich.funeralservices.repository.CeremonyScheduleRepository;
import ru.vladovich.funeralservices.repository.EmployeeRepository;
import ru.vladovich.funeralservices.repository.FuneralOrderRepository;

@Service
@RequiredArgsConstructor
public class CeremonyScheduleService {

    private final CeremonyScheduleRepository ceremonyScheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final FuneralOrderRepository funeralOrderRepository;

    public List<CeremonySchedule> findAll() {
        return ceremonyScheduleRepository.findAll();
    }

    public List<CeremonySchedule> findByEmployee(Long employeeId) {
        return ceremonyScheduleRepository.findByEmployeeId(employeeId);
    }

    public CeremonySchedule findById(Long id) {
        return ceremonyScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ceremony schedule not found with id: " + id));
    }

    public CeremonySchedule create(CeremonyScheduleRequest request) {
        CeremonySchedule schedule = new CeremonySchedule();
        fillSchedule(schedule, request);
        return ceremonyScheduleRepository.save(schedule);
    }

    public CeremonySchedule update(Long id, CeremonyScheduleRequest request) {
        CeremonySchedule schedule = findById(id);
        fillSchedule(schedule, request);
        return ceremonyScheduleRepository.save(schedule);
    }

    public void delete(Long id) {
        CeremonySchedule schedule = findById(id);
        ceremonyScheduleRepository.delete(schedule);
    }

    private void fillSchedule(CeremonySchedule schedule, CeremonyScheduleRequest request) {
        schedule.setCeremonyDate(request.getCeremonyDate());
        schedule.setCeremonyTime(request.getCeremonyTime());
        schedule.setAddress(request.getAddress());
        schedule.setEmployee(findEmployee(request.getEmployeeId()));
        schedule.setFuneralOrder(findFuneralOrder(request.getFuneralOrderId()));
    }

    private Employee findEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    private FuneralOrder findFuneralOrder(Long id) {
        return funeralOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funeral order not found with id: " + id));
    }
}
