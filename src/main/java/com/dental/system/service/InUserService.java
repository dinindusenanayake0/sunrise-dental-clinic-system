package com.dental.system.service;

import com.dental.system.model.User;

public interface InUserService {
    User login(String username, String password);
}
