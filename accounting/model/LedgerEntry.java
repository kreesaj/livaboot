package com.accounting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ledger_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgerEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 30)
    private String entryNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull(message = "Account is required")
    private Account account;
    
    @Column(name = "entry_date", nullable = false)
    @NotNull(message = "Entry date is required")
    private LocalDate entryDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private EntryType type;
    
    @Column(nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 50)
    private String referenceNumber;
    
    @Column(name = "balance_after", precision = 15, scale = 2)
    private BigDecimal balanceAfter;
    
    @Column(name = "is_reconciled")
    private Boolean isReconciled = false;
    
    @Column(name = "reconciled_date")
    private LocalDate reconciledDate;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "ledgerEntry", cascade = CascadeType.ALL)
    private List<LedgerEntryTransaction> ledgerEntryTransactions;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (entryNumber == null) {
            entryNumber = generateEntryNumber();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    private String generateEntryNumber() {
        return "LE" + System.currentTimeMillis();
    }
    
    public enum EntryType {
        DEBIT,
        CREDIT
    }
}
