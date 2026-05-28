package ru.vladovich.funeralservices.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vladovich.funeralservices.entity.FuneralOrder;

public interface FuneralOrderRepository extends JpaRepository<FuneralOrder, Long> {

    List<FuneralOrder> findByClientId(Long clientId);
}
