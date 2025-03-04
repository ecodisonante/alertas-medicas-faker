package com.alertasmedicas.app.faker.services;

import com.alertasmedicas.app.faker.dto.FakerDTO;
import com.alertasmedicas.app.faker.dto.MeasurementDTO;
import java.util.List;

public interface FakerService {

    List<FakerDTO> getFakerList();

    List<MeasurementDTO> getAnomaliesList();
}
