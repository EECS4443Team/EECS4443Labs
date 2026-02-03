package com.example.eecs4443lab.ui.auth.login;



import com.example.eecs4443lab.data.LoginRepository;
import com.example.eecs4443lab.data.Result;
import com.example.eecs4443lab.data.model.LoggedInUser;
import com.example.eecs4443lab.R;
import com.example.eecs4443lab.ui.auth.AuthViewModel;

public class LoginViewModel extends AuthViewModel<LoginResult> {

    private final LoginRepository repository;

    // Injects repository dependency
    LoginViewModel(LoginRepository loginRepository) {
        this.repository = loginRepository;
    }

    // Performs login and updates result LiveData
    @Override
    public void submit(String username, String password) {
        Result<LoggedInUser> r = repository.login(username, password);
        if (r instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) r).getData();
            result.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            result.setValue(new LoginResult(R.string.login_failed));
        }
    }
}