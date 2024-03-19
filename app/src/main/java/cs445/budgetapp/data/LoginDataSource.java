package cs445.budgetapp.data;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import cs445.budgetapp.MainActivity;
import cs445.budgetapp.data.model.LoggedInUser;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public LoginDataSource(Context context){
        this.context = context;
    }

    private Context context;


    public Result<LoggedInUser> login(String username, String password) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(context,"Sign in was successful!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(context, "Username or Password incorrect", Toast.LENGTH_LONG).show();
                    }
                });

           // get display name from sharedPref
            LoggedInUser newuser =
                    new LoggedInUser(
                            username,
                            "Jane Doe");
            return new Result.Success<>(newuser);
    }

    public void logout() {
        // TODO: revoke authentication
    }
}