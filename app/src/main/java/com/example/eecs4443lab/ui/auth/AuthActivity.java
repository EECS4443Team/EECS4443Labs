package com.example.eecs4443lab.ui.auth;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public abstract class AuthActivity<VM extends AuthViewModel<R>, R> extends AppCompatActivity {
    protected VM vm;

    protected abstract VM createViewModel();
    protected abstract EditText usernameField();
    protected abstract EditText passwordField();
    protected abstract Button submitButton();
    protected abstract ProgressBar loadingBar();

    protected abstract boolean isSuccess(R r);
    protected abstract @Nullable Integer getErrorResId(R r);
    protected abstract void onSuccess(R r);
        protected void setUI() {
            vm = createViewModel();

            vm.getFormState().observe(this, s-> {
                if (s == null) return;
                submitButton().setEnabled(s.isDataValid());
                if (s.getUsernameError() != null) usernameField().setError(getString(s.getUsernameError()));
                if (s.getPasswordError() != null) passwordField().setError(getString(s.getPasswordError()));
            });

            vm.getResult().observe(this, r -> {
                if (r == null) return;
                loadingBar().setVisibility(View.GONE);

                Integer err = getErrorResId(r);
                if (err != null) showFailed(err);

                if (isSuccess(r)) onSuccess(r);
                setResult(Activity.RESULT_OK);
            });

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
                    vm.dataChanged(usernameField().getText().toString(),
                            passwordField().getText().toString());
                }
            };
            usernameField().addTextChangedListener(afterTextChangedListener);
            passwordField().addTextChangedListener(afterTextChangedListener);

            submitButton().setOnClickListener(v -> {
                loadingBar().setVisibility(View.VISIBLE);
                try {
                    vm.submit(
                            usernameField().getText().toString(),
                            passwordField().getText().toString()
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    protected void showFailed(@StringRes int errorRes) {
        Toast.makeText(getApplicationContext(), errorRes, Toast.LENGTH_SHORT).show();
    }
        }



