package com.alertasmedicas.app.faker.services;

import java.util.List;
import com.alertasmedicas.app.faker.dto.PatientDTO;

public interface PatientService {

    List<PatientDTO> getPatients();

    List<PatientDTO> getPatientsByIdDoctor(String idDoctor);

}
