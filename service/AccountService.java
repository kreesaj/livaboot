package com.accounting.service;

import com.accounting.model.Account;
import com.accounting.model.AccountHead;
import com.accounting.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    
    private final AccountRepository accountRepository;
    
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    
    public List<Account> getActiveAccounts() {
        return accountRepository.findByIsActiveTrue();
    }
    
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }
    
    public Account getAccountByCode(String code) {
        return accountRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Account not found with code: " + code));
    }
    
    public List<Account> getAccountsByAccountHead(Long accountHeadId) {
        return accountRepository.findByAccountHeadId(accountHeadId);
    }
    
    public List<Account> getAccountsByType(AccountHead.AccountType type) {
        return accountRepository.findByAccountHeadType(type);
    }
    
    public Account createAccount(Account account) {
        if (accountRepository.findByCode(account.getCode()).isPresent()) {
            throw new RuntimeException("Account with code " + account.getCode() + " already exists");
        }
        if (account.getBalance() == null) {
            account.setBalance(account.getOpeningBalance());
        }
        return accountRepository.save(account);
    }
    
    public Account updateAccount(Long id, Account account) {
        Account existing = getAccountById(id);
        existing.setName(account.getName());
        existing.setAccountHeadid(account.getAccountHeadid());
        existing.setDescription(account.getDescription());
        existing.setIsActive(account.getIsActive());
        existing.setCurrency(account.getCurrency());
        return accountRepository.save(existing);
    }
    
    public Account updateAccountBalance(Long id, BigDecimal amount, boolean isDebit) {
        Account account = getAccountById(id);
        if (isDebit) {
            account.setBalance(account.getBalance().add(amount));
        } else {
            account.setBalance(account.getBalance().subtract(amount));
        }
        return accountRepository.save(account);
    }
    
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
