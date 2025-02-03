package com.alertasmedicas.app.faker.services;

import com.alertasmedicas.app.faker.dto.AnomalyDTO;
import com.alertasmedicas.app.faker.dto.FakerDTO;

import java.util.List;

public interface QueueService {

    boolean enqueueFakerList(List<FakerDTO> fakerList);

    boolean enqueueAnomaly(List<AnomalyDTO> anomalyList);

}
