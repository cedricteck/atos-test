package com.worldline.fpl.recruitment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.sun.javafx.beans.IDProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Account entity
 * 
 * @author A525125
 *
 */
@Data
@Entity
public class Account implements Serializable {

	private static final long serialVersionUID = -3548441891975414771L;

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String number;

	@Column
	private String type;

	@Column
	private BigDecimal balance;

	@Column(name = "creation_date")
	private String creationDate;

	@Column(name= "active")
	private boolean isActive;
}
