package com.Boujorno.adnanemanager.service.interfac;

import com.Boujorno.adnanemanager.dto.LoginRequest;
import com.Boujorno.adnanemanager.dto.Response;
import com.Boujorno.adnanemanager.model.User;

public interface IUserService {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}