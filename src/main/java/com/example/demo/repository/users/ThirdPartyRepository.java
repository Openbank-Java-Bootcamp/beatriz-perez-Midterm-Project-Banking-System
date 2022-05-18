package com.example.demo.repository.users;

import com.example.demo.model.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {

    ThirdParty findByUsername(String username);

}
