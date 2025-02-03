package com.alertasmedicas.app.faker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alertasmedicas.app.faker.dto.VitalSignDTO;

import java.util.List;

@Service
public class VitalSignServiceImpl implements VitalSignService {

    private final RestTemplate restTemplate;

    @Value("${api.vitalsigns:}")
    private String domain;

    @Autowired
    public VitalSignServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<VitalSignDTO> getVitalSigns() {
        String url = domain + "/vitalSign/getVitalSigns";
        ResponseEntity<List<VitalSignDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

}
