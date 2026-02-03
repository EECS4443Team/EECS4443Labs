package com.example.eecs4443lab.data;

import java.io.IOException;

public class RegisterRepository {
    private static volatile RegisterRepository instance;
    private final RegisterDataSource dataSource;

    public RegisterRepository(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegisterRepository getInstance(RegisterDataSource dataSource) {
        if (instance == null) {
            instance = new RegisterRepository(dataSource);
        }
        return instance;
    }

    public Result<Void> register(String username, String password) throws IOException {
        return dataSource.register(username, password);
    }
}
