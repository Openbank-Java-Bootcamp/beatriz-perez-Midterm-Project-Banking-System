package com.example.demo.repository.accounts;

import com.example.demo.model.accounts.Account;
import com.example.demo.model.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByPrimaryOwner(AccountHolder primaryOwner);
    List<Account> findAllBySecondaryOwner(AccountHolder secondaryOwner);

}
