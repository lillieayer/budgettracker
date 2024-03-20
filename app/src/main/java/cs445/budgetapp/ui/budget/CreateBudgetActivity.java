package cs445.budgetapp.ui.budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Calendar;

import cs445.budgetapp.MainActivity;
import cs445.budgetapp.MyApplication;
import cs445.budgetapp.R;
import cs445.budgetapp.ui.login.LoginActivity;
import cs445.budgetapp.ui.profile.ProfileActivity;

public class CreateBudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Spinner categorySpinner = findViewById(R.id.edit_budget_category);
        Button saveButton = findViewById(R.id.saveButton);
        // Find UI elements
        EditText editName = findViewById(R.id.edit_budget_name);
        EditText editStartDate = findViewById(R.id.edit_budget_start_date);
        EditText editEndDate = findViewById(R.id.edit_budget_end_date);
        EditText editAmt = findViewById(R.id.edit_budget_amt);
        EditText editComment = findViewById(R.id.edit_budget_comment);


        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://www.nerdwallet.com/article/finance/nerdwallet-budget-calculator");
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(false);
        webSettings.setAllowFileAccess(false);


        MyApplication app = (MyApplication) getApplication();
        // get user to access auth email
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser = mAuth.getCurrentUser();
        // eliminate poor regex
        String userPath = currUser.getUid();

        // initialize db reference for access
        DatabaseReference userData = app.getDb().getReference("Users/"+ userPath);

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String budgetName = editName.getText().toString();
                saveButton.setEnabled(!budgetName.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        saveButton.setOnClickListener(view -> {
            // get user input
            String budgetName = editName.getText().toString();
            String budgetAmt = editAmt.getText().toString();
            String budgetStart = editStartDate.getText().toString();
            String budgetEnd = editEndDate.getText().toString();
            String budgetComment = editComment.getText().toString();
            Budget new_budget = new Budget(budgetName, categorySpinner.getSelectedItem().toString(), budgetStart, budgetEnd, budgetAmt,budgetComment);

            // make unique key for each budget's storage
            String key = userData.push().getKey();
            if(key != null){
                userData.child(key).setValue(new_budget);
            } else{
                Log.w("failure","Error storing new budget, null key");
            }

            Calendar beginTime = Calendar.getInstance();
            beginTime.set(Integer.parseInt(budgetStart.substring(6,10)), Integer.parseInt(budgetStart.substring(0,2)), Integer.parseInt(budgetStart.substring(3,5)), 12, 00);
            Calendar endTime = Calendar.getInstance();
            endTime.set(Integer.parseInt(budgetEnd.substring(6,10)), Integer.parseInt(budgetEnd.substring(0,2)), Integer.parseInt(budgetEnd.substring(3,5)), 11, 59);
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, budgetName)
                    .putExtra(CalendarContract.Events.DESCRIPTION, budgetComment);
            Intent chooser = Intent.createChooser(intent,"Select a calendar app");
            try {
                startActivity(chooser);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Must download a calendar app to add budget events", Toast.LENGTH_LONG).show();
            }



            // clear fields
            editName.setText("");
            editAmt.setText("");
            editStartDate.setText("");
            editComment.setText("");
            editEndDate.setText("");


        });

        FloatingActionButton logoutButton = findViewById(R.id.logout_button_budget);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                mAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user == null) {
                            // User is signed out
                            Toast.makeText(CreateBudgetActivity.this, "Logging out", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CreateBudgetActivity.this, LoginActivity.class);
                            startActivity(intent);
                            firebaseAuth.removeAuthStateListener(this); // Remove the listener
                        }
                    }
                });
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_budget);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.budget_activity_container);

        // Perform item selected listener
        bottomNavigationView.setOnItemReselectedListener(item -> {
            int pageId = item.getItemId();

            if(pageId == R.id.navigation_budget){
                Toast.makeText(this, "Already on budget page!", Toast.LENGTH_SHORT).show();
            }
            else if(pageId == R.id.navigation_profile) {
                startActivity(new Intent(CreateBudgetActivity.this, ProfileActivity.class));
            }
            else  {
                startActivity(new Intent(CreateBudgetActivity.this, MainActivity.class));
            }

        });

        MaterialToolbar appBar = findViewById(R.id.toolbar_budget);
        MasterKey masterKey = null;
        try {
            masterKey = new MasterKey.Builder(this)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SharedPreferences sharedPreferences = null;
        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    this,
                    "budgetSharedPreferences",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String userId = currUser.getUid();
        String income = sharedPreferences.getString(userId, "");
        appBar.setTitle("Income: $" + income);


    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    }

}

