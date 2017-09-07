package com.worldline.fpl.recruitment.dao;

import com.worldline.fpl.recruitment.json.AddUpdateTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worldline.fpl.recruitment.entity.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Transaction repository
 * 
 * @author A525125
 *
 */
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {




	/**
	 * Get transactions by account
	 * 
	 * @param accountId
	 *            the account id
	 * @param p
	 *            the pageable information
	 * @return
	 */
	Page<Transaction> findByAccountId(Long accountId, Pageable p);

	/**
	 * Find a transaction with a specific account and transaction id
	 * @param id
	 * @param accountId
	 * @return
	 */
	Transaction findByIdAndAccountId(Long id, Long accountId);

}
