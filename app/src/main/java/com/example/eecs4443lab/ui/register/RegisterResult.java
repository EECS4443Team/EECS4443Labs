package com.example.eecs4443lab.ui.register;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class RegisterResult {
    @Nullable private final Integer error;
    @Nullable private final Boolean success;

    RegisterResult(@Nullable @StringRes Integer error) { this.error = error; this.success = null; }
    RegisterResult(boolean success) { this.success = success; this.error = null; }

    @Nullable Integer getError() { return error; }
    @Nullable Boolean getSuccess() { return success; }
}
