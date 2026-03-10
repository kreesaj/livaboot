package com.accounting.controller;

import com.accounting.model.AccountHead;
import com.accounting.service.AccountHeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/account-heads")
@CrossOrigin(origins = "http://localhost:4201")
@RequiredArgsConstructor
public class AccountHeadController {
    
    private final AccountHeadService accountHeadService;
    
    @GetMapping
    public ResponseEntity<List<AccountHead>> getAllAccountHeads() {
    	System.out.println("Here");
        return ResponseEntity.ok(accountHeadService.getAllAccountHeads());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<AccountHead>> getActiveAccountHeads() {
        return ResponseEntity.ok(accountHeadService.getActiveAccountHeads());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AccountHead> getAccountHeadById(@PathVariable Long id) {
        return ResponseEntity.ok(accountHeadService.getAccountHeadById(id));
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<AccountHead> getAccountHeadByCode(@PathVariable String code) {
        return ResponseEntity.ok(accountHeadService.getAccountHeadByCode(code));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<AccountHead>> getAccountHeadsByType(@PathVariable AccountHead.AccountType type) {
        return ResponseEntity.ok(accountHeadService.getAccountHeadsByType(type));
    }
    
    @PostMapping
    public ResponseEntity<AccountHead> createAccountHead(@Valid @RequestBody AccountHead accountHead) {
        return new ResponseEntity<>(accountHeadService.createAccountHead(accountHead), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AccountHead> updateAccountHead(@PathVariable Long id, 
                                                         @Valid @RequestBody AccountHead accountHead) {
        return ResponseEntity.ok(accountHeadService.updateAccountHead(id, accountHead));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountHead(@PathVariable Long id) {
        accountHeadService.deleteAccountHead(id);
        return ResponseEntity.noContent().build();
    }
}
