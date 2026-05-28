package ru.vladovich.funeralservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CeremonyScheduleRequest {

    @NotNull(message = "Ceremony date is required")
    private LocalDate ceremonyDate;

    @NotNull(message = "Ceremony time is required")
    private LocalTime ceremonyTime;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Employee id is required")
    private Long employeeId;

    @NotNull(message = "Funeral order id is required")
    private Long funeralOrderId;
}
