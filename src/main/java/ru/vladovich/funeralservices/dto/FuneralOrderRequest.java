package ru.vladovich.funeralservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuneralOrderRequest {

    @NotBlank(message = "Service name is required")
    private String serviceName;

    @NotNull(message = "Ceremony date is required")
    private LocalDate ceremonyDate;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Client id is required")
    private Long clientId;

    private Long employeeId;
}
