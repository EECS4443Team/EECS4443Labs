package com.example.eecs4443lab.ui.register;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eecs4443lab.databinding.ActivityRegisterBinding;
import com.example.eecs4443lab.util.CredentialStore;
import com.example.eecs4443lab.util.TextMaskToggleUtil;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText usernameEditText = binding.usernameView.editTextUsernameInput;
        final EditText passwordEditText = binding.passwordView.editTextPasswordInput;
        final View registerButton = binding.buttonRegister;
        final ProgressBar loadingProgressBar = binding.loading;

        final CredentialStore store = new CredentialStore(getApplicationContext());
        TextMaskToggleUtil.attach(
                binding.passwordView.editTextPasswordInput,
                binding.passwordView.ivPasswordToggle
        );
        registerButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);

            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();


            try {
                store.register(username, password);
                Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                loadingProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
