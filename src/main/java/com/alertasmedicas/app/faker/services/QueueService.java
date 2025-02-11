package com.alertasmedicas.app.faker.services;

import com.alertasmedicas.app.faker.dto.FakerDTO;
import com.alertasmedicas.app.faker.dto.MeasurementDTO;

import java.util.List;

public interface QueueService {

    boolean enqueueFakerList(List<FakerDTO> fakerList);

    boolean enqueueAnomaly(List<MeasurementDTO> anomalyList);

}
