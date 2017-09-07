package com.worldline.fpl.recruitment.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worldline.fpl.recruitment.entity.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Account repository
 * 
 * @author A525125
 *
 */
public interface AccountRepository extends PagingAndSortingRepository<Account, Long>{


}
