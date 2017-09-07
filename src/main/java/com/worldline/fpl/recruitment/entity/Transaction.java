package com.worldline.fpl.recruitment.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

import javax.persistence.*;

/**
 * Transaction entity
 * 
 * @author A525125
 *
 */
@Data
@Entity(name = "account_transaction")
public class Transaction implements Serializable {

	private static final long serialVersionUID = 706690724306325415L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@Column
	private String number;

	@Column
	private BigDecimal balance;
}
