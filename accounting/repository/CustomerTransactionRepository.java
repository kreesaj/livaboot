package com.accounting.repository;

import com.accounting.model.CustomerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerTransactionRepository extends JpaRepository<CustomerTransaction, Long> {
    List<CustomerTransaction> findByCustomerId(Long customerId);
    List<CustomerTransaction> findByStatus(CustomerTransaction.PaymentStatus status);
    List<CustomerTransaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
    List<CustomerTransaction> findByCustomerIdAndStatus(Long customerId, CustomerTransaction.PaymentStatus status);
    
    @Query("SELECT ct FROM CustomerTransaction ct WHERE ct.dueDate < :date AND ct.status != 'PAID'")
    List<CustomerTransaction> findOverdueTransactions(LocalDate date);
}
