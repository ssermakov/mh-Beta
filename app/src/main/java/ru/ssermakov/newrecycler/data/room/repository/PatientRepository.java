package ru.ssermakov.newrecycler.data.room.repository;

import java.util.List;

import ru.ssermakov.newrecycler.data.room.entity.Patient;
import ru.ssermakov.newrecycler.data.room.dao.PatientDao;

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

    public Long insertPatientToDb (Patient patient) {
        return patientDao.insert(patient);
    }

    public void updatePatientInDb (Patient patient) {
        patientDao.update(patient);
    }

    public void deletePatientFromDb (Patient patient) {
        patientDao.delete(patient);
    }
}
