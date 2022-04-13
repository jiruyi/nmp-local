package com.matrictime.network.service;

import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.model.Result;
import org.springframework.stereotype.Service;


public interface UserFriendsService {

    Result<Integer> cancelUser(UserRequest userRequest);
}
