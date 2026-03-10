package com.accounting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 30)
    private String transactionNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Customer is required")
    private Customer customer;
    
    @Column(name = "transaction_date", nullable = false)
    @NotNull(message = "Transaction date is required")
    private LocalDate transactionDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;
    
    @Column(nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 50)
    private String referenceNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status = PaymentStatus.PENDING;
    
    @Column(name = "due_date")
    private LocalDate dueDate;
    
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 20)
    private PaymentMethod paymentMethod;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (transactionNumber == null) {
            transactionNumber = generateTransactionNumber();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    private String generateTransactionNumber() {
        return "TXN" + System.currentTimeMillis();
    }
    
    public enum TransactionType {
        INVOICE,
        PAYMENT,
        CREDIT_NOTE,
        DEBIT_NOTE
    }
    
    public enum PaymentStatus {
        PENDING,
        PARTIAL,
        PAID,
        OVERDUE,
        CANCELLED
    }
    
    public enum PaymentMethod {
        CASH,
        CHECK,
        BANK_TRANSFER,
        CREDIT_CARD,
        DEBIT_CARD,
        ONLINE,
        OTHER
    }
}
