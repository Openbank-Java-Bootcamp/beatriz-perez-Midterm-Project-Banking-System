package com.example.demo.service.impl.users;

import com.example.demo.model.users.ThirdParty;
import com.example.demo.repository.users.ThirdPartyRepository;
import com.example.demo.repository.users.UserRepository;
import com.example.demo.service.interfaces.users.ThirdPartyServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThirdPartyService implements ThirdPartyServiceInterface {

    @Autowired
    private ThirdPartyRepository ThirdPartyRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Methods
    public ThirdParty saveThirdParty(ThirdParty thirdParty) {
        // Handle possible errors:
        if(ThirdPartyRepo.findByUsername(thirdParty.getUsername()) != null) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Element already exists" ); }
        // Save new user:
        log.info("Saving a new thirdParty {} in the DB", thirdParty.getUsername());
        thirdParty.setPassword(passwordEncoder.encode(thirdParty.getPassword()));
        return ThirdPartyRepo.save(thirdParty);
    }

}
