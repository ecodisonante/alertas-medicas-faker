package com.alertasmedicas.app.faker.dto;

import java.util.List;

public record FakerDTO(
        PatientDTO patient,
        List<MeasurementDTO> measurements
) {}
