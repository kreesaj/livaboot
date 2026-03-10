package com.accounting.service;

import com.accounting.model.AccountHead;
import com.accounting.repository.AccountHeadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountHeadService {
    
    private final AccountHeadRepository accountHeadRepository;
    
    public List<AccountHead> getAllAccountHeads() {
    	
        return accountHeadRepository.findAll();

        
    }
    
    public List<AccountHead> getActiveAccountHeads() {
    	System.out.println("inside this 1");
        return accountHeadRepository.findByIsActiveTrue();
    }
    
    public AccountHead getAccountHeadById(Long id) {
        return accountHeadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account Head not found with id: " + id));
    }
    
    public AccountHead getAccountHeadByCode(String code) {
        return accountHeadRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Account Head not found with code: " + code));
    }
    
    public List<AccountHead> getAccountHeadsByType(AccountHead.AccountType type) {
        return accountHeadRepository.findByType(type);
    }
    
    public AccountHead createAccountHead(AccountHead accountHead) {
        if (accountHeadRepository.findByCode(accountHead.getCode()).isPresent()) {
            throw new RuntimeException("Account Head with code " + accountHead.getCode() + " already exists");
        }
        return accountHeadRepository.save(accountHead);
    }
    
    public AccountHead updateAccountHead(Long id, AccountHead accountHead) {
        AccountHead existing = getAccountHeadById(id);
        existing.setName(accountHead.getName());
        existing.setType(accountHead.getType());
        existing.setDescription(accountHead.getDescription());
        existing.setIsActive(accountHead.getIsActive());
        return accountHeadRepository.save(existing);
    }
    
    public void deleteAccountHead(Long id) {
        accountHeadRepository.deleteById(id);
    }
}
