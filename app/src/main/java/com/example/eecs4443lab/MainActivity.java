package com.example.eecs4443lab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.example.eecs4443lab.ui.auth.login.LoginActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ensureCredentialFile(this);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();



    }

    public static void ensureCredentialFile(Context context) {
        File outFile = new File(context.getFilesDir(), "loginCredential.txt");
        if (outFile.exists()) return; // Prevent duplicates

        try (InputStream in = context.getAssets().open("loginCredential.txt");
             OutputStream out = new FileOutputStream(outFile)) {

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
