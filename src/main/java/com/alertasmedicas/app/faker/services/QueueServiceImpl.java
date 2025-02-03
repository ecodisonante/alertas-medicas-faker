package com.alertasmedicas.app.faker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alertasmedicas.app.faker.dto.AnomalyDTO;
import com.alertasmedicas.app.faker.dto.FakerDTO;

import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@Service
public class QueueServiceImpl implements QueueService {

    private final RestTemplate restTemplate;

    @Value("${api.queue:}")
    private String domain;

    @Autowired
    public QueueServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean enqueueFakerList(List<FakerDTO> fakerList) {
        String url = domain + "/send/pacientMeasures";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<FakerDTO>> requestEntity = new HttpEntity<>(fakerList, headers);

        try {

            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class);

            // Retorna true si el estado es 200-299
            return response.getStatusCode().is2xxSuccessful();

        } catch (Exception e) {
            log.error("Error al enviar mediciones: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean enqueueAnomaly(List<AnomalyDTO> anomalyList) {
        String url = domain + "/send/anomaly";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<AnomalyDTO>> requestEntity = new HttpEntity<>(anomalyList, headers);

        try {

            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class);

            // Retorna true si el estado es 200-299
            return response.getStatusCode().is2xxSuccessful();

        } catch (Exception e) {
            log.error("Error al enviar anomalias: " + e.getMessage());
            return false;
        }
    }
}
