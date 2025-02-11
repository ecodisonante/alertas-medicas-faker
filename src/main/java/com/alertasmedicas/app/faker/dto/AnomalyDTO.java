package com.alertasmedicas.app.faker.dto;

import java.time.LocalDateTime;

public record AnomalyDTO(
        long idPatient,
        String status,
        LocalDateTime date
) {}