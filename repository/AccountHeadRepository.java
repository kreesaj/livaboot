package com.accounting.repository;

import com.accounting.model.AccountHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountHeadRepository extends JpaRepository<AccountHead, Long> {
    Optional<AccountHead> findByCode(String code);
    List<AccountHead> findByType(AccountHead.AccountType type);
    List<AccountHead> findByIsActiveTrue();
}
