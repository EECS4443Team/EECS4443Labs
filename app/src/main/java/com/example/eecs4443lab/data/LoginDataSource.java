package com.example.eecs4443lab.data;

import android.content.Context;

import com.example.eecs4443lab.data.model.LoggedInUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class LoginDataSource  {

    private final File credentialFile;

    public LoginDataSource(Context context) {
        //data/loginCredential.txt
        this.credentialFile = new File(context.getFilesDir(), "loginCredential.txt");
    }

    public Result<LoggedInUser> login(String username, String password) {
        if (!credentialFile.exists()) {
            return new Result.Error(new IOException("No registered user (credential file not found)"));
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(credentialFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String fileUsername = parseValue(line, "username");
                if (fileUsername == null) continue;

                String nextLine = reader.readLine();
                //make sure pair of username and password
                if (nextLine == null) {
                    return new Result.Error(new IOException("Corrupted credential file: missing password line"));
                }

                String filePassword = parseValue(nextLine, "password");
                //check the pair
                if (fileUsername.equals(username) && filePassword.equals(password)) {
                    LoggedInUser user = new LoggedInUser(UUID.randomUUID().toString(), fileUsername);
                    return new Result.Success<>(user);
                }

            }

            return new Result.Error(new IOException("Invalid credentials"));

        } catch (IOException e) {
            return new Result.Error(new IOException("Error reading credential file", e));
        }
    }

    public void logout() {
        // no-op (local auth)
    }

    /**
     * formats like:
     *   username=alice
     *   password=1234
     */
    private static String parseValue(String line, String key) {
        if (line == null) return null;

        String trimmed = line.trim();

        // allow both "key=" and "key ="
        String prefix1 = key + "=";


        if (trimmed.startsWith(prefix1)) {
            return trimmed.substring(prefix1.length()).trim();
        }

        return null;
    }
}
