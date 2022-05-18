package com.example.demo.repository.users;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    AccountHolder findByUsername(String username);

}
