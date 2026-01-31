package com.example.eecs4443lab.ui.register;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.eecs4443lab.util.CredentialStore;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {

    private final Context appContext;

    public RegisterViewModelFactory(Context context) {
        this.appContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(new CredentialStore(appContext));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
