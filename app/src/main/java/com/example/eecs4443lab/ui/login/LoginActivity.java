package com.example.eecs4443lab.ui.login;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.eecs4443lab.HomeActivity;
import com.example.eecs4443lab.R;
import com.example.eecs4443lab.databinding.ActivityMainBinding;
import com.example.eecs4443lab.databinding.ViewPasswordBinding;
import com.example.eecs4443lab.ui.register.RegisterActivity;
import com.example.eecs4443lab.util.TextMaskToggleUtil;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(getApplicationContext()))
                .get(LoginViewModel.class);

        // Declare sharedPreferences
        SharedPreferences prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);

        // Check if sharedPreferences has stored values - if so, go straight to welcome page
        if (prefs.getBoolean("loggedIn", false)) {
            loginViewModel.login(prefs.getString("username", "aaaa"),
                    prefs.getString("password", "bbbb"));
        }

        com.example.eecs4443lab.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        try {
            setContentView(binding.getRoot());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


        ViewPasswordBinding passwordBinding = binding.passwordView;
        final EditText usernameEditText = binding.usernameView.editTextUsernameInput;
        final EditText passwordEditText = passwordBinding.editTextPasswordInput;
        final Button loginButton = binding.buttonLogin;
        final ProgressBar loadingProgressBar = binding.loading;
        final Button registerButton = binding.buttonRegister;
        final Button cancelButton = binding.button;
        final CheckBox rememberMeCheckbox = binding.checkBoxRememberMe;

        // Attaches password visibility toggle (eye icon)
        TextMaskToggleUtil.attach(
                passwordBinding.editTextPasswordInput,
                passwordBinding.ivPasswordToggle
        );

        // Observes form validation state and updates UI errors + login button enabled state
        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        // Observes login result and handles success/failure UI flow
        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                // Check if remember me is checked, use sharedPreferences to store login for remember me
                if (rememberMeCheckbox.isChecked()) {
                    prefs.edit().putBoolean("loggedIn", true).apply();
                    prefs.edit().putString("username", loginResult.getSuccess().getDisplayName()).apply();
                    prefs.edit().putString("password", passwordEditText.getText().toString()).apply();
                }
                // Navigates to Home screen on successful login
                updateUiWithUser(loginResult.getSuccess());
            }
            // Marks this activity result as OK for callers
            setResult(Activity.RESULT_OK);
        });

        // Validates input whenever user edits username/password
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Updates ViewModel with latest input for validation
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        // Triggers login when keyboard "Done" is pressed on password field
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        // Clears inputs and resets UI state
        cancelButton.setOnClickListener(v -> {
            usernameEditText.setText("");
            passwordEditText.setText("");

            usernameEditText.setError(null);
            passwordEditText.setError(null);
            usernameEditText.requestFocus();

            loadingProgressBar.setVisibility(View.GONE);

        });

        // Starts login process and shows loading indicator
        loginButton.setOnClickListener(v -> {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());

            });
        // Navigates to Register screen
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent (LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // Shows welcome message and navigates to HomeActivity with username extra
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("username",
                model.getDisplayName());
        startActivity(intent);
        finish();
    }
    // Displays login failure message
    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
