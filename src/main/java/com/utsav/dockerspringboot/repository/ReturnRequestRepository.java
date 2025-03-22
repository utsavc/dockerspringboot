package com.utsav.dockerspringboot.repository;

import com.utsav.dockerspringboot.model.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {
    List<ReturnRequest> findByStatus(String status);
    long countByStatus(String status);
}
