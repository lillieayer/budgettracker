package cs445.budgetapp.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Patterns;


import cs445.budgetapp.MyApplication;
import cs445.budgetapp.data.LoginRepository;

import cs445.budgetapp.R;
import cs445.budgetapp.data.Result;
import cs445.budgetapp.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    private Context context;

    LoginViewModel(LoginRepository loginRepository, Context context) {
        this.loginRepository = loginRepository;
        this.context = context;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));

        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }


    private boolean isUserNameValid(String username) {
        CredentialValidation validation = new CredentialValidation();
        boolean valid = validation.isEmailValid(username);
        return valid;
    }

    private boolean isPasswordValid(String password) {
        CredentialValidation validation = new CredentialValidation();
        boolean valid = validation.isPasswordValid(password);
        return valid;
    }
}