package com.accounting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Account code is required")
    @Column(unique = true, nullable = false, length = 20)
    private String code;
    
    @NotBlank(message = "Account name is required")
    @Column(nullable = false, length = 100)
    private String name;
    
	/*
	 * 
	 * 
	 * @JoinColumn(name = "account_head_id", nullable = false)
	 */
      //@ManyToOne(fetch = FetchType.LAZY)
	  @NotNull(message = "Account head is required")
	  @Column ( name = "account_head_id" ,nullable = false, length = 100)
	  private  int accountHeadid; 	  
	 
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
    
    @Column(name = "opening_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal openingBalance = BigDecimal.ZERO;
    
    @Column(length = 3)
    private String currency = "INR";
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<LedgerEntry> ledgerEntries;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        if (openingBalance == null) {
            openingBalance = BigDecimal.ZERO;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
