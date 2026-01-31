package com.example.eecs4443lab.ui.register;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eecs4443lab.R;
import com.example.eecs4443lab.util.CredentialStore;
import com.example.eecs4443lab.data.Result;

import java.io.IOException;

public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<RegisterFormState> formState = new MutableLiveData<>();
    private final MutableLiveData<RegisterResult> result = new MutableLiveData<>();
    private final CredentialStore store;

    RegisterViewModel(CredentialStore store) {
        this.store = store;
    }

    LiveData<RegisterFormState> getFormState() { return formState; }
    LiveData<RegisterResult> getResult() { return result; }

    public void register(String username, String password) throws IOException {
        Result<Void> r = store.register(username, password);

        if (r instanceof Result.Success) {
            result.setValue(new RegisterResult(true));
        } else {
            result.setValue(new RegisterResult(R.string.register_failed));
        }
    }

    public void dataChanged(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            formState.setValue(new RegisterFormState(R.string.invalid_username, null));
        } else if (password == null || password.trim().length() <= 5) {
            formState.setValue(new RegisterFormState(null, R.string.invalid_password));
        } else {
            formState.setValue(new RegisterFormState(true));
        }
    }
}