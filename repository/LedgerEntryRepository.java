package com.accounting.repository;

import com.accounting.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {
    List<LedgerEntry> findByAccountId(Long accountId);
    List<LedgerEntry> findByEntryDateBetween(LocalDate startDate, LocalDate endDate);
    List<LedgerEntry> findByAccountIdAndEntryDateBetween(Long accountId, LocalDate startDate, LocalDate endDate);
    List<LedgerEntry> findByIsReconciledFalse();
}
