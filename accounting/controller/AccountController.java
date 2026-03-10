package com.accounting.controller;

import com.accounting.model.Account;
import com.accounting.model.AccountHead;
import com.accounting.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:4201")
@RequiredArgsConstructor
public class AccountController {
    
    private final AccountService accountService;
    
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Account>> getActiveAccounts() {
        return ResponseEntity.ok(accountService.getActiveAccounts());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<Account> getAccountByCode(@PathVariable String code) {
        return ResponseEntity.ok(accountService.getAccountByCode(code));
    }
    
	
	  @GetMapping("/account-head/{accountHeadId}") public
	  ResponseEntity<List<Account>> getAccountsByAccountHead(@PathVariable Long
	  accountHeadId) { return
	  ResponseEntity.ok(accountService.getAccountsByAccountHead(accountHeadId)); }
	 
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Account>> getAccountsByType(@PathVariable AccountHead.AccountType type) {
        return ResponseEntity.ok(accountService.getAccountsByType(type));
    }
    
    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, 
                                                 @Valid @RequestBody Account account) {
        return ResponseEntity.ok(accountService.updateAccount(id, account));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
