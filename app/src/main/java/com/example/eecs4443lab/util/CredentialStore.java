package com.example.eecs4443lab.util;

import android.content.Context;

import com.example.eecs4443lab.data.Result;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Stores credentials in internal storage as pairs:
 * username=<value>
 * password=<value>
 * Rule: password must be the next line after its username line.
 */
public final class CredentialStore {

    private static final String FILE_NAME = "loginCredential.txt";
    private static final String USER_PREFIX = "username=";
    private static final String PASS_PREFIX = "password=";

    private final File credentialFile;

    // Initializes credential file location using app context
    public CredentialStore(Context context) {
        this.credentialFile = new File(context.getFilesDir(), FILE_NAME);
    }

    /**
     * Registers a new user into the credential file.
     * - Validates input
     * - Checks for duplicate username/password
     * - Appends new credentials if valid
     */
    public Result<Void> register(String username, String password) throws IOException {
        username = normalize(username);
        password = normalize(password);
        // Validates required fields
        if (username.isEmpty()) throw new IOException("Username required");
        if (password.isEmpty()) throw new IOException("Password required");

        // Scans file for duplicate entries
        DuplicateCheck dup = scanDuplicates(username, password);

        if (dup.usernameExists) throw new IOException("Username already exists");

        if (dup.passwordExists) throw new IOException("Password already exists");
        //prevent duplicates

        // Appends new username/password pair
        appendPair(username, password);
        return null;
    }

    // Appends a username-password pair to the file
    private void appendPair(String username, String password) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(credentialFile, true))) {
            bw.write(USER_PREFIX + username);
            bw.newLine();
            bw.write(PASS_PREFIX + password);
            bw.newLine();
        }
    }
    // Scans file to detect duplicate usernames or passwords
    private DuplicateCheck scanDuplicates(String targetUsername, String targetPassword) throws IOException {
        boolean usernameExists = false;
        boolean passwordExists = false;

        if (!credentialFile.exists()) {
            return new DuplicateCheck(false, false);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(credentialFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(USER_PREFIX)) {
                    continue; //only username can be the starting point
                }

                String fileUsername = line.substring(USER_PREFIX.length()).trim();
                String nextLine = br.readLine();
                // Ensures file format consistency
                if (nextLine == null) {
                    throw new IOException("Corrupted credential file: missing password line");
                }
                if (!nextLine.startsWith(PASS_PREFIX)) {
                    throw new IOException("Corrupted credential file: username not followed by password");
                }

                String filePassword = nextLine.substring(PASS_PREFIX.length()).trim();

                // Compares stored values with input
                if (fileUsername.equals(targetUsername)) usernameExists = true;
                if (filePassword.equals(targetPassword)) passwordExists = true;

                if (usernameExists || passwordExists) break;
            }
        }

        return new DuplicateCheck(usernameExists, passwordExists);
    }

    // Normalizes input by trimming whitespace and handling null
    private static String normalize(String s) {
        return s == null ? "" : s.trim();
    }
    // Simple container for duplicate check results
    private static final class DuplicateCheck {
        final boolean usernameExists;
        final boolean passwordExists;

        DuplicateCheck(boolean usernameExists, boolean passwordExists) {
            this.usernameExists = usernameExists;
            this.passwordExists = passwordExists;
        }
    }
}
