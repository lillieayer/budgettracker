package cs445.budgetapp.data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

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
                        Log.d("Success", "signInWithEmail:success");
                        Toast.makeText(context,"Sign in was successful!", Toast.LENGTH_LONG);
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(context, "Username or Password incorrect", Toast.LENGTH_LONG);
                        Log.w("Fail", "signInWithEmail:failure", task.getException());
                    }
                });

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser newuser =
                    new LoggedInUser(
                            username,
                            "Jane Doe");
            return new Result.Success<>(newuser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}