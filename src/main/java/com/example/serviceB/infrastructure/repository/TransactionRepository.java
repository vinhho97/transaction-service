package com.example.serviceB.infrastructure.repository;

import com.example.serviceB.domain.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDetail, UUID> {
}
