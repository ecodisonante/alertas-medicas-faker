package com.alertasmedicas.app.faker.dto;

public record VitalSignDTO(
        Long id,
        String name,
        Double lowerLimit,
        Double upperLimit,
        String lowerName,
        String upperName
) {}
