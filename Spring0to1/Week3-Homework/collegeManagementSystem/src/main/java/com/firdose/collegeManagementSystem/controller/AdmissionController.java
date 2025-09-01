package com.firdose.collegeManagementSystem.controller;

import com.firdose.collegeManagementSystem.entity.AdmissionRecordEntity;
import com.firdose.collegeManagementSystem.service.AdmissionRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admissions")
public class AdmissionController {

    private final AdmissionRecordService admissionRecordService;

    public AdmissionController(AdmissionRecordService admissionRecordService) {
        this.admissionRecordService = admissionRecordService;
    }


    @PostMapping(path = "/")
    public ResponseEntity<AdmissionRecordEntity> createAdmissionRecord(@RequestBody AdmissionRecordEntity admissionRecord){
        return new ResponseEntity<>(admissionRecordService.createAdmissionRecord(admissionRecord), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{admissionId}/assignAdmission/{studentId}")
    public ResponseEntity<AdmissionRecordEntity> assignAdmissionToStudent(@PathVariable long admissionId, @PathVariable long studentId){
        return ResponseEntity.ok(admissionRecordService.assignAdmissionToStudent(admissionId, studentId));
    }


}
