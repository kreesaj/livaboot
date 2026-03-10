package com.accounting.repository;

import com.accounting.model.CheckIssueDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckIssueDetailRepository extends JpaRepository<CheckIssueDetail, Long> {
    List<CheckIssueDetail> findByCheckRegisterId(Long checkRegisterId);
    List<CheckIssueDetail> findByExpenseAccountId(Long expenseAccountId);
}
