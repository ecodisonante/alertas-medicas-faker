package com.alertasmedicas.app.faker.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alertasmedicas.app.faker.dto.FakerDTO;
import com.alertasmedicas.app.faker.services.FakerService;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/faker")
public class FakerController {

    private final FakerService fakerService;

    @Autowired
    public FakerController(FakerService fakerService) {
        this.fakerService = fakerService;
    }

    @GetMapping("/fakeAllPatients")
    public ResponseEntity<List<FakerDTO>> fakeAllPatients() {
        log.info("🚀 Generando mediciones para todos los pacientes...");
        try {
            List<FakerDTO> fakers = this.fakerService.getFakerList();
            log.info("📌 Lista de fakers generada exitosamente. Total: {} pacientes.", fakers.size());
            return ResponseEntity.ok(fakers);
        } catch (Exception e) {
            log.error("❌ Error al generar la lista de fakers: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
