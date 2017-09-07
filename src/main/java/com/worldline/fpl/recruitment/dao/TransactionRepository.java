package com.worldline.fpl.recruitment.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worldline.fpl.recruitment.entity.Transaction;

/**
 * Transaction repository
 * 
 * @author A525125
 *
 */
public interface TransactionRepository {


	/**
	 * Get transaction by Id
	 *
	 * @param id
	 *            id of the transaction to get
	 * @return the transaction corresponding to the given id or null
	 */
	Transaction findById(String id);

	/**
	 * Get transactions by account
	 * 
	 * @param accountId
	 *            the account id
	 * @param p
	 *            the pageable information
	 * @return
	 */
	Page<Transaction> getTransactionsByAccount(String accountId, Pageable p);

	/**
	 * Delete a transaction
	 * @param transactionId
	 */
	void deleteTransaction(String transactionId);

	/**
	 * Check if a transaction exist
	 * @param transactionId
	 * @return
	 */
	boolean exist(String transactionId);

	/**
	 * Check if a transaction belong to a specific account
	 * @param accountId
	 * @param transactionId
	 * @return
	 */
	boolean transactionBelongToAccount(String accountId, String transactionId);

	/**
	 * Ajoute une transaction a un account
	 * @param accountId
	 * @param transaction
	 * @return
	 */
	Transaction add(String accountId, Transaction transaction);
}
