package com.accounting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "check_register")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckRegister {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Check number is required")
    @Column(unique = true, nullable = false, length = 20)
    private String checkNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", nullable = false)
    @NotNull(message = "Bank account is required")
    private Account bankAccount;
    
    @Column(name = "check_date", nullable = false)
    @NotNull(message = "Check date is required")
    private LocalDate checkDate;
    
    @Column(nullable = false, length = 100)
    @NotBlank(message = "Payee is required")
    private String payee;
    
    @Column(nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    
    @Column(columnDefinition = "TEXT")
    private String memo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CheckStatus status = CheckStatus.ISSUED;
    
    @Column(name = "cleared_date")
    private LocalDate clearedDate;
    
    @Column(name = "void_date")
    private LocalDate voidDate;
    
    @Column(name = "void_reason", columnDefinition = "TEXT")
    private String voidReason;
    
    @Column(name = "is_printed")
    private Boolean isPrinted = false;
    
    @Column(name = "print_date")
    private LocalDateTime printDate;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "checkRegister", cascade = CascadeType.ALL)
    private List<CheckIssueDetail> checkIssueDetails;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum CheckStatus {
        ISSUED,
        CLEARED,
        VOID,
        OUTSTANDING,
        CANCELLED,
        STOPPED
    }
}
