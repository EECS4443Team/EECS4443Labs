package com.example.eecs4443lab.ui.auth.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eecs4443lab.R;
import com.example.eecs4443lab.data.RegisterRepository;
import com.example.eecs4443lab.data.Result;
import com.example.eecs4443lab.ui.auth.FormState;
import com.example.eecs4443lab.ui.auth.AuthViewModel;

import java.io.IOException;

public class RegisterViewModel extends AuthViewModel<RegisterResult> {

    private final MutableLiveData<FormState> formState = new MutableLiveData<>();
    private final MutableLiveData<RegisterResult> result = new MutableLiveData<>();
    private final RegisterRepository registerRepository;

    public RegisterViewModel(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    @Override
    public LiveData<FormState> getFormState() { return formState; }

    @Override
    public LiveData<RegisterResult> getResult() { return result; }

    @Override
    public void dataChanged(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            formState.setValue(new FormState(R.string.invalid_username, null));
        } else if (password == null || password.trim().length() <= 5) {
            formState.setValue(new FormState(null, R.string.invalid_password));
        } else {
            formState.setValue(new FormState(true));
        }
    }

    @Override
    public void submit(String username, String password) throws IOException {
        Result<Void> r = registerRepository.register(username, password);

        if (r instanceof Result.Success) {
            result.setValue(new RegisterResult(true));
        } else {
            result.setValue(new RegisterResult(R.string.register_failed));
        }
    }
}
