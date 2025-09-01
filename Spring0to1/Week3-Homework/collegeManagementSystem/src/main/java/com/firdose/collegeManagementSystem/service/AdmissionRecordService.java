package com.firdose.collegeManagementSystem.service;

import com.firdose.collegeManagementSystem.entity.AdmissionRecordEntity;
import org.springframework.stereotype.Service;


public interface AdmissionRecordService {
    public AdmissionRecordEntity createAdmissionRecord(AdmissionRecordEntity admissionRecord);

    AdmissionRecordEntity assignAdmissionToStudent(long admissionId, long studentId);
}
