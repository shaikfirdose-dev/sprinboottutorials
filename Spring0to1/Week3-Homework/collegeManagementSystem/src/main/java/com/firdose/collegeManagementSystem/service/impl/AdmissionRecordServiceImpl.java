package com.firdose.collegeManagementSystem.service.impl;

import com.firdose.collegeManagementSystem.entity.AdmissionRecordEntity;
import com.firdose.collegeManagementSystem.entity.StudentEntity;
import com.firdose.collegeManagementSystem.repository.AdmissionRecordRepository;
import com.firdose.collegeManagementSystem.repository.StudentRepository;
import com.firdose.collegeManagementSystem.service.AdmissionRecordService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdmissionRecordServiceImpl implements AdmissionRecordService {
    private final AdmissionRecordRepository admissionRecordRepository;
    private final StudentRepository studentRepository;

    public AdmissionRecordServiceImpl(AdmissionRecordRepository admissionRecordRepository, StudentRepository studentRepository) {
        this.admissionRecordRepository = admissionRecordRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public AdmissionRecordEntity createAdmissionRecord(AdmissionRecordEntity admissionRecord) {
        return admissionRecordRepository.save(admissionRecord);
    }

    @Override
    public AdmissionRecordEntity assignAdmissionToStudent(long admissionId, long studentId) {
        Optional<StudentEntity> savedStudent = studentRepository.findById(studentId);
        Optional<AdmissionRecordEntity> savedAdmissionRecord = admissionRecordRepository.findById(admissionId);

        return savedStudent.flatMap(
                student -> savedAdmissionRecord.map(
                        admission -> {
                            admission.setStudent(student);
                            return admission;
                        }

                )
        ).orElse(null);


    }
}
