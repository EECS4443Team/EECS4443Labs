package com.example.eecs4443lab.ui.auth.login;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.eecs4443lab.data.LoginDataSource;
import com.example.eecs4443lab.data.LoginRepository;


/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final Context appContext;

    public LoginViewModelFactory(Context context) {
        this.appContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            LoginDataSource ds = new LoginDataSource(appContext);
            return (T) new LoginViewModel(LoginRepository.getInstance(ds));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}