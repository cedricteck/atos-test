package com.worldline.fpl.recruitment.controller.impl;

import com.worldline.fpl.recruitment.json.AddUpdateTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldline.fpl.recruitment.controller.TransactionController;
import com.worldline.fpl.recruitment.json.TransactionResponse;
import com.worldline.fpl.recruitment.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link TransactionController}
 * 
 * @author A525125
 *
 */
@Slf4j
@RestController
public class TransactionControllerImpl implements TransactionController {

	private TransactionService transactionService;

	@Autowired
	public TransactionControllerImpl(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Override
	public ResponseEntity<Page<TransactionResponse>> getTransactionsByAccount(
			@PathVariable("accountId") String accountId,
			@PageableDefault Pageable p) {
		Page<TransactionResponse> page = transactionService
				.getTransactionsByAccount(accountId, p);
		if (null == page || page.getTotalElements() == 0) {
			log.debug("Cannot find transaction for account {}", accountId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.ok().body(page);
	}

	@Override
	public ResponseEntity<Page> deleteTransaction(
			@PathVariable("accountId") String accountId,
			@PathVariable("transactionId") String transactionId){

		transactionService.removeTransaction(accountId, transactionId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@Override
	public ResponseEntity<TransactionResponse> addTransaction(
			@PathVariable("accountId") String accountId,
			@Validated @RequestBody AddUpdateTransaction addUpdateTransaction){

		return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.addTransaction(accountId,addUpdateTransaction));

	}
}
