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

        assert binding.usernameView != null;
        final EditText usernameEditText = binding.usernameView.editTextUsernameInput;
        assert binding.passwordView != null;
        final EditText passwordEditText = binding.passwordView.editTextPasswordInput;
        final View registerButton = binding.buttonRegister;
        final ProgressBar loadingProgressBar = binding.loading;

        // Creates credential store for file-based registration
        final CredentialStore store = new CredentialStore(getApplicationContext());
        // Attaches password visibility toggle (eye icon)
        TextMaskToggleUtil.attach(
                binding.passwordView.editTextPasswordInput,
                binding.passwordView.ivPasswordToggle
        );
        // Handles register button click and writes credentials to internal file
        assert registerButton != null;
        registerButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);

            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();


            try {
                // Attempts to register
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
