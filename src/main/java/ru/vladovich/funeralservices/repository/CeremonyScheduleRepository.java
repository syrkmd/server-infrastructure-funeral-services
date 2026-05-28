package ru.vladovich.funeralservices.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vladovich.funeralservices.entity.CeremonySchedule;

public interface CeremonyScheduleRepository extends JpaRepository<CeremonySchedule, Long> {

    List<CeremonySchedule> findByEmployeeId(Long employeeId);
}
