package com.example.demo.service.interfaces.users;

import com.example.demo.model.users.ThirdParty;

import java.util.List;

public interface ThirdPartyServiceInterface {

    // GET A LIST OF ALL THIRD PARTIES
    List<ThirdParty> getAllThirdParties();

    // CREATE A NEW THIRD PARTY
    ThirdParty createThirdParty(ThirdParty thirdParty);

    // UPDATE A THIRD PARTY BY ID
    void updateThirdPartyById(Long id, ThirdParty thirdParty);

    // DELETE A THIRD PARTY BY ID
    void deleteThirdPartyById(Long id);

}
