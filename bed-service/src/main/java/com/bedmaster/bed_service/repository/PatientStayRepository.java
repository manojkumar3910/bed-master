package com.bedmaster.bed_service.repository;

import com.bedmaster.bed_service.entity.PatientStay;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PatientStayRepository extends JpaRepository<PatientStay,Long> {
    PatientStay findByCurrentBedID(Long bedId);//sample
//    List<PatientStay> findByPatientID(Long PatientID  );
}
