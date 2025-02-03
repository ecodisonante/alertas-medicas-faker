package com.alertasmedicas.app.faker.services;

import java.util.List;

import com.alertasmedicas.app.faker.dto.VitalSignDTO;

public interface VitalSignService {
    List<VitalSignDTO> getVitalSigns();
}
