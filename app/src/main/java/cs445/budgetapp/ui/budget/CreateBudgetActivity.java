package cs445.budgetapp.ui.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import cs445.budgetapp.MainActivity;
import cs445.budgetapp.MyApplication;
import cs445.budgetapp.R;
import cs445.budgetapp.ui.profile.ProfileActivity;

public class CreateBudgetActivity extends AppCompatActivity {

    String budgetCategory, budgetName;

    private MyApplication app;

    private DatabaseReference userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Spinner categorySpinner = findViewById(R.id.edit_budget_category);
        Button saveButton = findViewById(R.id.saveButton);
        // Find UI elements
        EditText editName = findViewById(R.id.edit_budget_name);
        EditText editDate = findViewById(R.id.edit_budget_start_date);
        EditText editAmt = findViewById(R.id.edit_budget_amt);
        EditText editComment = findViewById(R.id.edit_budget_comment);
        EditText editEndDate = findViewById(R.id.edit_budget_end_date);

        editAmt.setText("0.00");

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://www.nerdwallet.com/article/finance/nerdwallet-budget-calculator");
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(false);


        app = (MyApplication) getApplication();
        // get user to access auth email
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        // eliminate poor regex
        String[] userPathArr = currUser.getEmail().split("[@.]");
        String userPath = String.join("",userPathArr);

        // initialize db reference for access
        userData = app.getDb().getReference("Users/"+ userPath);

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                budgetName = editName.getText().toString();
                saveButton.setEnabled(!budgetName.isEmpty());

            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                budgetCategory = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveButton.setOnClickListener(view -> {
            // get user input
            String budgetAmt = editAmt.getText().toString();
            String budgetDate = editDate.getText().toString();
            String budgetComment = editComment.getText().toString();
            String bdugetEndDate = editEndDate.getText().toString();
            Budget new_budget = new Budget(budgetName, budgetCategory, budgetDate, budgetAmt,budgetComment);

            // make unique key for each budget's storage
            String key = userData.push().getKey();
            if(key != null){
                userData.child(key).setValue(new_budget);
            } else{
                Log.w("failure","Error storing new budget, null key");
            }

            // clear fields
            editName.setText("");
            editAmt.setText("");
            editDate.setText("");
            editComment.setText("");
            editEndDate.setText("");


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
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if ("www.example.com".equals(request.getUrl().getHost())) {
                // This is your website, so don't override. Let your WebView load the
                // page.
                return false;
            }
            // Otherwise, the link isn't for a page on your site, so launch another
            // Activity that handles URLs.
            Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
            startActivity(intent);
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userData = null;
        budgetCategory = null;
        budgetName = null;
        app = null;
    }


}

