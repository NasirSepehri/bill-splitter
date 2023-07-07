package com.example.billsplitter.repo;

import com.example.billsplitter.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CostRepository extends JpaRepository<Cost, Long> {

    List<Cost> findAllByEvent_IdOrderByIdAsc(Long eventId);
}
