package com.example.eecs4443lab.ui.auth;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;

public abstract class AuthViewModel<R> extends ViewModel {
    protected final MutableLiveData<FormState> formState = new MutableLiveData<>();
    protected final MutableLiveData<R> result = new MutableLiveData<>();

    public LiveData<FormState> getFormState() { return formState; }
    public LiveData<R> getResult() { return result; }
    public void dataChanged(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            formState.setValue(new FormState(com.example.eecs4443lab.R.string.invalid_username, null));
        } else if (password == null || password.trim().length() <= 5) {
            formState.setValue(new FormState(null, com.example.eecs4443lab.R.string.invalid_password));
        } else {
            formState.setValue(new FormState(true));
        }
    }
    protected boolean isUserNameValid(String username) {
        if (username == null) return false;
        if (username.contains("@")) return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        return !username.trim().isEmpty();
    }
    protected boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
    public abstract void submit(String username, String password) throws IOException;
}
