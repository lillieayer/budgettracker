package cs445.budgetapp.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


import cs445.budgetapp.R;


public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        EditText createUser = view.findViewById(R.id.createUsername);
        EditText createPW = view.findViewById(R.id.createPassword);
        Button signupButton = view.findViewById(R.id.signupButton);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(view1 -> {
            String email = createUser.getText().toString();
            String password = createPW.getText().toString();
            createUser.setText("");
            createPW.setText("");
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((OnCompleteListener<AuthResult>) task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Success", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            LinearLayout createAccount = view.findViewById(R.id.createAccountContainer);
                            createAccount.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "Account Created! Log in to continue!", Toast.LENGTH_LONG).show();
                        } else {
                            // Sign up failed
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                // User already exists
                                Toast.makeText(getContext(), "Account already exists! You must log in", Toast.LENGTH_SHORT).show();
                            } else {
                                // Other error
                                // If sign in fails, display a message to the user.
                                Log.w("failure", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getContext(), "Sign in failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        });

        return view;

    }
}