package com.alertasmedicas.app.faker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alertasmedicas.app.faker.dto.FakerDTO;
import com.alertasmedicas.app.faker.dto.MeasurementDTO;
import com.alertasmedicas.app.faker.dto.PatientDTO;
import com.alertasmedicas.app.faker.dto.VitalSignDTO;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Log4j2
@Service
public class FakerServiceImpl implements FakerService {

    private final Random random = new Random();

    private final PatientService patientService;
    private final VitalSignService vitalSignService;
    private final QueueService queueService;

    private List<FakerDTO> fakerList = new ArrayList<>();
    private List<MeasurementDTO> anomaliesList = new ArrayList<>();
    private List<VitalSignDTO> signList = new ArrayList<>();

    @Autowired
    public FakerServiceImpl(PatientService patientService, VitalSignService vitalSignService,
            QueueService queueService) {
        this.patientService = patientService;
        this.vitalSignService = vitalSignService;
        this.queueService = queueService;
    }

    @PostConstruct // ejecuta automaticamente cuando inicia el microservicio
    public void init() {
        log.info("🚀 Faker iniciado");
        executeMeasurementFaker();
    }

    @Scheduled(fixedRateString = "${execution.time:300000}") // Tiempo de reejecucion
    public void executeMeasurementFaker() {
        try {
            log.info("🚀 Generando mediciones...");

            this.fakerList = generateFakerList();
            this.anomaliesList = generateAnomaliesList(fakerList);

            log.info("✅ Mediciones generadas: {}", fakerList.size());
            log.info("🔥 Anomalías detectadas: {}", anomaliesList.size());

            if (!fakerList.isEmpty())
                sendFakerListToProductor();
            if (!this.anomaliesList.isEmpty())
                sendAnomalyListToProductor();

        } catch (Exception e) {
            log.error("❌ Error al generar lista de mediciones: {}\n{}", e.getMessage());
            log.error(e);
        }
    }

    @Override
    public List<FakerDTO> getFakerList() {
        return fakerList;
    }

    @Override
    public List<MeasurementDTO> getAnomaliesList() {
        return anomaliesList;
    }

    private List<FakerDTO> generateFakerList() {
        List<FakerDTO> newFakerList = new ArrayList<>();

        var patientList = patientService.getPatients();
        this.signList = vitalSignService.getVitalSigns();

        for (PatientDTO patient : patientList) {
            var faker = new FakerDTO(patient, new ArrayList<>());

            for (VitalSignDTO sign : signList) {
                var measurement = generateMeasurement(patient.id(), sign);
                faker.measurements().add(measurement);
            }

            newFakerList.add(faker);
        }

        return newFakerList;
    }

    private List<MeasurementDTO> generateAnomaliesList(List<FakerDTO> fakerList) {

        if (this.signList.isEmpty())
            this.signList = vitalSignService.getVitalSigns();

        return fakerList.stream() // Stream de fakers
                .flatMap(faker -> faker.measurements().stream() // Stream de mediciones de cada faker
                        .flatMap(measurement -> signList.stream()
                                .filter(sign -> sign.id().equals(measurement.idSing()) &&
                                        (measurement.measurementValue() > sign.upperLimit() ||
                                                measurement.measurementValue() < sign.lowerLimit()))
                                .map(sign -> measurement) // Si cumple la condición, agregar medición
                        ))
                .toList();
    }

    public MeasurementDTO generateMeasurement(Long patientId, VitalSignDTO vitalSign) {

        // Generar una variacion para cada tipo de signo vital
        double variation = getVariationRange(vitalSign.id()) * (random.nextDouble() > 0.5 ? 1 : -1);

        double lowerLimit = vitalSign.lowerLimit() - variation;
        double upperLimit = vitalSign.upperLimit() + variation;

        double randomValue = lowerLimit + (upperLimit - lowerLimit) * random.nextDouble();
        double roundedValue = BigDecimal.valueOf(randomValue).setScale(1, RoundingMode.HALF_UP).doubleValue();

        return new MeasurementDTO(null, patientId, vitalSign.id(), roundedValue, LocalDateTime.now());
    }

    private double getVariationRange(long vitalSignName) {
        return switch ((int) vitalSignName) {
            case 1 -> 1.5; // Temperatura
            case 2 -> 20.0; // Frecuencia Cardíaca
            case 3 -> 20.0; // Presión Arterial
            case 4 -> 4.0; // Frecuencia Respiratoria
            case 5 -> 8.0; // Saturación de Oxígeno
            default -> 5.0; // Variación genérica por defecto
        };
    }

    private boolean sendFakerListToProductor() {
        return queueService.enqueueFakerList(fakerList);
    }

    private boolean sendAnomalyListToProductor() {
        return queueService.enqueueAnomaly(anomaliesList);
    }

}
