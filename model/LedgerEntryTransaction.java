package com.accounting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger_entry_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgerEntryTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_entry_id", nullable = false)
    @NotNull(message = "Ledger entry is required")
    private LedgerEntry ledgerEntry;
    
    @Column(nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Debit amount is required")
    private BigDecimal debitAmount = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Credit amount is required")
    private BigDecimal creditAmount = BigDecimal.ZERO;
    
    @Column(columnDefinition = "TEXT")
    private String particulars;
    
    @Column(length = 50)
    private String voucherType;
    
    @Column(length = 50)
    private String voucherNumber;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
