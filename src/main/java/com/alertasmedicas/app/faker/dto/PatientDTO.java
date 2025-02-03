package com.alertasmedicas.app.faker.dto;

public record PatientDTO(
        Long id,
        Long idDoctor,
        String name,
        String state
) {}
