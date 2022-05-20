package com.example.demo.service.impl.users;

import com.example.demo.model.users.ThirdParty;
import com.example.demo.model.users.User;
import com.example.demo.repository.users.ThirdPartyRepository;
import com.example.demo.service.interfaces.users.ThirdPartyServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThirdPartyService implements ThirdPartyServiceInterface {

    @Autowired
    private ThirdPartyRepository ThirdPartyRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Methods

    // GET A LIST OF ALL THIRD PARTIES
    public List<ThirdParty> getAllThirdParties() {
        // Handle possible errors:
        if(ThirdPartyRepo.findAll().size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Return results
        log.info("Fetching all Third Parties");
        return ThirdPartyRepo.findAll();
    }

    // CREATE A NEW THIRD PARTY
    public ThirdParty createThirdParty(ThirdParty thirdParty) {
        // Handle possible errors:
        if(ThirdPartyRepo.findByUsername(thirdParty.getUsername()).isPresent()) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Element already exists" ); }
        // Encrypt secret key:
        thirdParty.setPassword(passwordEncoder.encode(thirdParty.getPassword()));
        // Save new user:
        log.info("Saving a new thirdParty {} in the DB", thirdParty.getUsername());
        return ThirdPartyRepo.save(thirdParty);
    }

    // UPDATE A THIRD PARTY BY ID
    public void updateThirdPartyById(Long id, ThirdParty thirdParty) {
        Optional<ThirdParty> oldThirdParty = ThirdPartyRepo.findById(id);
        // Handle possible errors:
        if(oldThirdParty.isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No ThirdParty found with the specified ID"); }
        // Update thirdParty:
        log.info("Updating thirdParty");
        thirdParty.setId(oldThirdParty.get().getId());
        ThirdPartyRepo.save(thirdParty);
    }

    // DELETE A THIRD PARTY BY ID
    public void deleteThirdPartyById(Long id) {
        Optional<ThirdParty> thirdParty = ThirdPartyRepo.findById(id);
        // Handle possible errors:
        if(thirdParty.isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No thirdParty found with the specified ID"); }
        // Delete user:
        log.info("Deleting thirdParty");
        ThirdPartyRepo.delete(thirdParty.get());
    }

}
