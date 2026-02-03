package com.example.eecs4443lab.ui.auth.register;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.eecs4443lab.databinding.ActivityRegisterBinding;
import com.example.eecs4443lab.ui.auth.AuthActivity;
import com.example.eecs4443lab.util.TextMaskToggleUtil;

import java.io.IOException;

public class RegisterActivity extends AuthActivity<RegisterViewModel, RegisterResult> {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Password visibility toggle
        TextMaskToggleUtil.attach(
                binding.passwordView.editTextPasswordInput,
                binding.passwordView.ivPasswordToggle
        );

        // 공통 observer/watcher/submit wiring
        setUI();

        // Done 키로 submit
        passwordField().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loadingBar().setVisibility(android.view.View.VISIBLE);
                try {
                    vm.submit(usernameField().getText().toString(),
                            passwordField().getText().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        });
    }

    // ====== AuthActivity abstract implementations ======

    @Override
    protected RegisterViewModel createViewModel() {
        return new ViewModelProvider(this, new RegisterViewModelFactory(getApplicationContext()))
                .get(RegisterViewModel.class);
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
        return (Button) binding.buttonRegister;
    }

    @Override
    protected ProgressBar loadingBar() {
        return binding.loading;
    }

    @Override
    protected boolean isSuccess(RegisterResult r) {
        return r.getSuccess() != null;
    }

    @Override
    protected @Nullable Integer getErrorResId(RegisterResult r) {
        return r.getError();
    }

    @Override
    protected void onSuccess(RegisterResult r) {
        setResult(RESULT_OK);
        finish();
    }
}
