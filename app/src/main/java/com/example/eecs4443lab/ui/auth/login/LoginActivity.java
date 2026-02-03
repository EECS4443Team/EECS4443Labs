package com.example.eecs4443lab.ui.auth.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.eecs4443lab.R;
import com.example.eecs4443lab.databinding.ActivityMainBinding;
import com.example.eecs4443lab.databinding.ViewPasswordBinding;
import com.example.eecs4443lab.ui.auth.AuthActivity;
import com.example.eecs4443lab.ui.auth.register.RegisterActivity;
import com.example.eecs4443lab.ui.home.HomeActivity;
import com.example.eecs4443lab.util.TextMaskToggleUtil;

public class LoginActivity extends AuthActivity<LoginViewModel, LoginResult> {

    private ActivityMainBinding binding;
    private SharedPreferences prefs;
    private CheckBox rememberMeCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
        rememberMeCheckbox = binding.checkBoxRememberMe;

        // Password visibility toggle
        ViewPasswordBinding passwordBinding = binding.passwordView;
        TextMaskToggleUtil.attach(
                passwordBinding.editTextPasswordInput,
                passwordBinding.ivPasswordToggle
        );

       setUI();

        passwordField().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loadingBar().setVisibility(View.VISIBLE);
                vm.submit(usernameField().getText().toString(),
                        passwordField().getText().toString());
            }
            return false;
        });

        //cancel button
        binding.button.setOnClickListener(v -> {
            usernameField().setText("");
            passwordField().setText("");

            usernameField().setError(null);
            passwordField().setError(null);
            usernameField().requestFocus();

            loadingBar().setVisibility(View.GONE);
        });

        // Navigate to Register Page
        binding.buttonRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        // Remember me
        if (prefs.getBoolean("loggedIn", false)) {
            loadingBar().setVisibility(View.VISIBLE);
            vm.submit(
                    prefs.getString("username", ""),
                    prefs.getString("password", "")
            );
        }
    }


    @Override
    protected LoginViewModel createViewModel() {
        return new ViewModelProvider(this, new LoginViewModelFactory(getApplicationContext()))
                .get(LoginViewModel.class);
    }

    @Override
    protected EditText usernameField() {
        return binding.usernameView.editTextUsernameInput;
    }

    @Override
    protected EditText passwordField() {
        return binding.passwordView.editTextPasswordInput;
    }

    @Override
    protected Button submitButton() {
        return binding.buttonLogin;
    }

    @Override
    protected ProgressBar loadingBar() {
        return binding.loading;
    }

    @Override
    protected boolean isSuccess(LoginResult r) {
        return r.getSuccess() != null;
    }

    @Override
    protected @Nullable Integer getErrorResId(LoginResult r) {
        return r.getError();
    }

    @Override
    protected void onSuccess(LoginResult r) {
        LoggedInUserView user = r.getSuccess();
        if (user == null) return;

        // Remember Me
        if (rememberMeCheckbox.isChecked()) {
            prefs.edit()
                    .putBoolean("loggedIn", true)
                    .putString("username", user.getDisplayName())
                    .putString("password", passwordField().getText().toString())
                    .apply();
        }

        // Navigate to Home
        updateUiWithUser(user);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("username", model.getDisplayName());
        startActivity(intent);
        finish();
    }
}
