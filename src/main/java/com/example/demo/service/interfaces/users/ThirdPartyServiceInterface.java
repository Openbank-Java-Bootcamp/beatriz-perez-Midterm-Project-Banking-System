package com.example.demo.service.interfaces.users;

import com.example.demo.model.users.ThirdParty;

public interface ThirdPartyServiceInterface {

    // CREATE A NEW THIRD PARTY
    ThirdParty createThirdParty(ThirdParty thirdParty);

    // UPDATE A THIRD PARTY BY ID
    void updateThirdPartyById(Long id, ThirdParty thirdParty);

}
