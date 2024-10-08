package cs445.budgetapp.ui.login;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import cs445.budgetapp.R;


public class SignUpFragment extends Fragment {

    Button signupButton;


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
        signupButton = view.findViewById(R.id.signupButton);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        createUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setSignUpButton(createUser.getText().toString(), createPW.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        createPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setSignUpButton(createUser.getText().toString(), createPW.getText().toString());

            }
        });



        signupButton.setOnClickListener(view1 -> {
            mAuth.createUserWithEmailAndPassword(createUser.getText().toString(), createPW.getText().toString())
                    .addOnCompleteListener((OnCompleteListener<AuthResult>) task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Success", "createUserWithEmail:success");
                            LinearLayout createAccount = view.findViewById(R.id.createAccountContainer);
                            createAccount.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(), "Account Created! Log in to continue!", Toast.LENGTH_LONG).show();
                        } else {
                            // Sign up failed
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                // User already exists
                                Toast.makeText(getActivity(), "Account already exists! You must log in", Toast.LENGTH_LONG).show();
                            } else {
                                // Other error
                                // If sign in fails, display a message to the user.
                                Log.w("failure", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getActivity(), "Sign in failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
            createUser.setText("");
            createPW.setText("");
        });

        return view;
    }




    private void setSignUpButton(String email, String pw){
        CredentialValidation validation = new CredentialValidation();
        if ((validation.isEmailValid(email)) && (validation.isPasswordValid(pw))) {
            signupButton.setEnabled(true);
        } else{
            signupButton.setEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        signupButton = null;
    }
}