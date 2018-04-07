package ru.ssermakov.newrecycler.data.room.repository;

import java.util.List;

import ru.ssermakov.newrecycler.data.room.Patient;
import ru.ssermakov.newrecycler.data.room.PatientDao;

public class PatientRepository {
    PatientDao patientDao;

    public PatientRepository(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public List<Patient> loadAllPatients () {
        return patientDao.getAll();
    }

    public Patient getPatientById (Long id) {
        return patientDao.getById(id) ;
    }

    void insertPatientToDb (Patient patient) {
        patientDao.insert(patient);
    }

    void updatePatientInDb (Patient patient) {
        patientDao.update(patient);
    }

    void deletePatientFromDb (Patient patient) {
        patientDao.delete(patient);
    }
}
