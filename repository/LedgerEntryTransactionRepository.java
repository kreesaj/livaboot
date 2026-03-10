package com.accounting.repository;

import com.accounting.model.LedgerEntryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerEntryTransactionRepository extends JpaRepository<LedgerEntryTransaction, Long> {
    List<LedgerEntryTransaction> findByLedgerEntryId(Long ledgerEntryId);
}
