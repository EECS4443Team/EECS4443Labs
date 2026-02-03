package com.example.eecs4443lab.data;

import com.example.eecs4443lab.util.CredentialStore;

import java.io.IOException;

public class RegisterDataSource {
    private final CredentialStore store;

    public RegisterDataSource(CredentialStore store) {
        this.store = store;
    }

    public Result<Void> register(String username, String password) throws IOException {
        return store.register(username, password);
    }
}
