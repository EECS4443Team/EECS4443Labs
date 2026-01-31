package com.example.eecs4443lab.data;

import com.example.eecs4443lab.data.model.LoggedInUser;

public interface AuthDataSource {
    Result<LoggedInUser> login(String username, String password);
    void logout();
}
