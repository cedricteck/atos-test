package com.worldline.fpl.recruitment.service;

import java.util.stream.Collectors;

import com.worldline.fpl.recruitment.json.AddUpdateTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.worldline.fpl.recruitment.dao.TransactionRepository;
import com.worldline.fpl.recruitment.entity.Transaction;
import com.worldline.fpl.recruitment.exception.ServiceException;
import com.worldline.fpl.recruitment.json.ErrorCode;
import com.worldline.fpl.recruitment.json.TransactionResponse;

/**
 * Transaction service
 * 
 * @author A525125
 *
 */
@Service
public class TransactionService {

	private AccountService accountService;

	private TransactionRepository transactionRepository;

	@Autowired
	public TransactionService(AccountService accountService,
			TransactionRepository transactionRepository) {
		this.accountService = accountService;
		this.transactionRepository = transactionRepository;
	}

	/**
	 * Get transactions by account
	 * 
	 * @param accountId
	 *            the account id
	 * @param p
	 *            the pageable object
	 * @return
	 */
	public Page<TransactionResponse> getTransactionsByAccount(String accountId,
			Pageable p) {
		if (!accountService.isAccountExist(accountId)) {
			throw new ServiceException(ErrorCode.NOT_FOUND_ACCOUNT,
					"Account doesn't exist");
		}
		return new PageImpl<TransactionResponse>(transactionRepository
				.getTransactionsByAccount(accountId, p).getContent().stream()
				.map(this::map).collect(Collectors.toList()));
	}

	public void removeTransaction(String accountId, String transactionId){

		if(!accountService.isAccountExist(accountId)){
			throw new ServiceException(ErrorCode.NOT_FOUND_ACCOUNT,
					"Account doesn't exist");
		}
		if(!isTransactionExist(transactionId)){
			throw new ServiceException(ErrorCode.NOT_FOUND_TRANSACTION,
					"Transaction doesn't exist");
		}
		if(!transactionBelongToAccount(accountId, transactionId)){
			throw new ServiceException(ErrorCode.FORBIDDEN_TRANSACTION,
					"Transaction doesn't belong to account");
		}

		transactionRepository.deleteTransaction(transactionId);

	}

	public TransactionResponse addTransaction(String accountId, AddUpdateTransaction addUpdateTransaction){
		if(!accountService.isAccountExist(accountId)){
			throw new ServiceException(ErrorCode.NOT_FOUND_ACCOUNT,
					"Account doesn't exist");
		}
		return map(transactionRepository.add(map(accountId, addUpdateTransaction)));
	}


	public TransactionResponse updateTransaction(String accountId, String transactionId, AddUpdateTransaction addUpdateTransaction){

		if(!accountService.isAccountExist(accountId)){
			throw new ServiceException(ErrorCode.NOT_FOUND_ACCOUNT,
					"Account doesn't exist");
		}
		if(!isTransactionExist(transactionId)){
			throw new ServiceException(ErrorCode.NOT_FOUND_TRANSACTION,
					"Transaction doesn't exist");
		}
		if(!transactionBelongToAccount(accountId, transactionId)){
			throw new ServiceException(ErrorCode.FORBIDDEN_TRANSACTION,
					"Transaction doesn't belong to account");
		}

		return map(transactionRepository.update(map(accountId, transactionId, addUpdateTransaction)));

	}

	/**
	 * Map {@link Transaction} to {@link TransactionResponse}
	 * 
	 * @param transaction
	 * @return
	 */
	private TransactionResponse map(Transaction transaction) {
		TransactionResponse result = new TransactionResponse();
		result.setBalance(transaction.getBalance());
		result.setId(transaction.getId());
		result.setNumber(transaction.getNumber());
		return result;
	}

	private Transaction map (String accountId, AddUpdateTransaction addUpdateTransaction){

		Transaction transaction = new Transaction();
		transaction.setAccountId(accountId);
		transaction.setNumber(addUpdateTransaction.getNumber());
		transaction.setBalance(addUpdateTransaction.getBalance());

		return transaction;
	}

	private Transaction map(String accountId, String transactionId, AddUpdateTransaction addUpdateTransaction){
		Transaction transaction = new Transaction();
		transaction.setAccountId(accountId);
		transaction.setId(transactionId);
		transaction.setNumber(addUpdateTransaction.getNumber());
		transaction.setBalance(addUpdateTransaction.getBalance());
		return transaction;
	}
	/**
	 * Check if a transaction exist
	 * @param transactionId
	 * @return
	 */
	public boolean isTransactionExist(String transactionId){
		return transactionRepository.exist(transactionId);
	}

	/**
	 * Check if a transaction belong to an account
	 * @param accountId
	 * @param transactionId
	 * @return
	 */
	public boolean transactionBelongToAccount(String accountId, String transactionId){
		return transactionRepository.transactionBelongToAccount(accountId, transactionId);
	}

}
