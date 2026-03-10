package com.accounting.service;

import com.accounting.model.AccountHead;
import com.accounting.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {
    
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CustomerTransactionRepository customerTransactionRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final CheckRegisterRepository checkRegisterRepository;
    
    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboard = new HashMap<>();
        
        // Financial Summary
        dashboard.put("totalAssets", calculateTotalAssets());
        dashboard.put("totalLiabilities", calculateTotalLiabilities());
        dashboard.put("totalRevenue", calculateTotalRevenue());
        dashboard.put("totalExpenses", calculateTotalExpenses());
        dashboard.put("netIncome", calculateNetIncome());
        
        // Customer Statistics
        dashboard.put("totalCustomers", customerRepository.count());
        dashboard.put("activeCustomers", customerRepository.findByIsActiveTrue().size());
        dashboard.put("totalCustomerBalance", calculateTotalCustomerBalance());
        
        // Transaction Statistics
        dashboard.put("pendingInvoices", customerTransactionRepository
                .findByStatus(com.accounting.model.CustomerTransaction.PaymentStatus.PENDING).size());
        dashboard.put("overdueInvoices", customerTransactionRepository
                .findOverdueTransactions(LocalDate.now()).size());
        
        // Check Statistics
        dashboard.put("outstandingChecks", checkRegisterRepository
                .findByStatus(com.accounting.model.CheckRegister.CheckStatus.OUTSTANDING).size());
        
        // Recent Ledger Entries
        dashboard.put("unreconciledEntries", ledgerEntryRepository.findByIsReconciledFalse().size());
        
        return dashboard;
    }
    
    private BigDecimal calculateTotalAssets() {
        return accountRepository.findByAccountHeadType(AccountHead.AccountType.ASSET)
                .stream()
                .map(account -> account.getBalance())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private BigDecimal calculateTotalLiabilities() {
        return accountRepository.findByAccountHeadType(AccountHead.AccountType.LIABILITY)
                .stream()
                .map(account -> account.getBalance())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private BigDecimal calculateTotalRevenue() {
        return accountRepository.findByAccountHeadType(AccountHead.AccountType.REVENUE)
                .stream()
                .map(account -> account.getBalance())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private BigDecimal calculateTotalExpenses() {
        return accountRepository.findByAccountHeadType(AccountHead.AccountType.EXPENSE)
                .stream()
                .map(account -> account.getBalance())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private BigDecimal calculateNetIncome() {
        return calculateTotalRevenue().subtract(calculateTotalExpenses());
    }
    
    private BigDecimal calculateTotalCustomerBalance() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> customer.getBalance())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
