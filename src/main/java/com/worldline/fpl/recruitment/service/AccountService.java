package com.worldline.fpl.recruitment.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.worldline.fpl.recruitment.dao.AccountRepository;
import com.worldline.fpl.recruitment.entity.Account;
import com.worldline.fpl.recruitment.exception.ServiceException;
import com.worldline.fpl.recruitment.json.AccountDetailsResponse;
import com.worldline.fpl.recruitment.json.AccountResponse;
import com.worldline.fpl.recruitment.json.ErrorCode;

import lombok.extern.slf4j.Slf4j;

/**
 * Account service
 * 
 * @author A525125
 *
 */
@Slf4j
@Service
public class AccountService {

	private AccountRepository accountRepository;

	@Autowired
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	/**
	 * Get account by user
	 * 
	 * @param p
	 *            the pageable information
	 * @return the account list
	 */
	public Page<AccountResponse> getAccounts(Pageable p) {
		return new PageImpl<AccountResponse>(accountRepository.findAll(p)
				.getContent().stream().map(this::mapToAccountResponse)
				.collect(Collectors.toList()));
	}

	/**
	 * Check if an account exists
	 * 
	 * @param accountId
	 *            the account id
	 * @return true if the account exists
	 */
	public boolean isAccountExist(String accountId) {
		return accountRepository.exists(Long.parseLong(accountId));
	}

	/**
	 * Find a account based on the id
	 * @param accountId
	 * @return
	 */
	public Account findOne(String accountId){
		return accountRepository.findOne(Long.parseLong(accountId));
	}

	/**
	 * Get account details
	 * 
	 * @param accountId
	 *            the account id
	 * @return
	 */
	public AccountDetailsResponse getAccountDetails(String accountId) {
		log.debug("Find account {}", accountId);

		try{
			Account account = accountRepository.findOne(Long.parseLong(accountId));
			if(account == null){
				throw  new ServiceException(ErrorCode.NOT_FOUND_ACCOUNT, "Account doesn't exist");
			}
			return mapToAccountDetailsResponse(account);
		}
		catch (NumberFormatException e){
			throw  new ServiceException(ErrorCode.NOT_FOUND_ACCOUNT, "Account doesn't exist");
		}

	}

	/**
	 * Map {@link Account} to {@link AccountResponse}
	 * 
	 * @param account
	 *            the entity
	 * @return the response
	 */
	private AccountResponse mapToAccountResponse(Account account) {
		AccountResponse result = new AccountResponse();
		result.setBalance(account.getBalance());
		result.setId(account.getId().toString());
		result.setNumber(account.getNumber());
		result.setType(account.getType());
		return result;
	}

	/**
	 * Map {@link Account} to {@link AccountDetailsResponse}
	 * 
	 * @param account
	 *            the entity
	 * @return the response
	 */
	private AccountDetailsResponse mapToAccountDetailsResponse(Account account) {
		AccountDetailsResponse result = new AccountDetailsResponse();
		result.setActive(account.isActive());
		result.setCreationDate(account.getCreationDate());
		result.setBalance(account.getBalance());
		result.setId(account.getId().toString());
		result.setNumber(account.getNumber());
		result.setType(account.getType());
		return result;
	}

}
