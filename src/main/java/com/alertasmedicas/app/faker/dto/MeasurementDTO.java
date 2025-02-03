package com.alertasmedicas.app.faker.dto;

import java.time.LocalDateTime;

public record MeasurementDTO(
        Long id,
        Long idPatient,
        Long idSing,
        double measurementValue,
        LocalDateTime dateTime
) {}
