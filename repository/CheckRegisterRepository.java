package com.accounting.repository;

import com.accounting.model.CheckRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheckRegisterRepository extends JpaRepository<CheckRegister, Long> {
    List<CheckRegister> findByBankAccountId(Long bankAccountId);
    List<CheckRegister> findByStatus(CheckRegister.CheckStatus status);
    List<CheckRegister> findByCheckDateBetween(LocalDate startDate, LocalDate endDate);
    List<CheckRegister> findByPayeeContainingIgnoreCase(String payee);
}
