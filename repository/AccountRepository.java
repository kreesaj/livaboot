package com.accounting.repository;

import com.accounting.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCode(String code);
    List<Account> findByAccountHeadid(Long accountHeadId);
    List<Account> findByIsActiveTrue();
    
	/*
	 * @Query("SELECT a FROM Account a WHERE a.accountHead.type = :type")
	 * List<Account>
	 * findByAccountHeadType(com.accounting.model.AccountHead.AccountType type);
	 */
}
