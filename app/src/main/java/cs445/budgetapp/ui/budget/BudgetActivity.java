package cs445.budgetapp.ui.budget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cs445.budgetapp.MainActivity;
import cs445.budgetapp.MyApplication;
import cs445.budgetapp.R;
import cs445.budgetapp.ui.profile.BudgetAdapter;
import cs445.budgetapp.ui.profile.ProfileActivity;

public class BudgetActivity extends AppCompatActivity {

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
        EditText editDate = findViewById(R.id.edit_budget_date);
        EditText editAmt = findViewById(R.id.edit_budget_amt);
        EditText editComment = findViewById(R.id.edit_budget_comment);

        editAmt.setText("0.00");

        app = (MyApplication) getApplication();
        // get user to access auth email
        FirebaseUser currUser = app.getAuthUser();

        String[] userPathArr = currUser.getEmail().split("@");
        String userPath = userPathArr[0];

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
                Log.v("Success", "Category chosen!");
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
            editAmt.setText("0.00");
            editDate.setText("");
            editComment.setText("");

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
                startActivity(new Intent(BudgetActivity.this, ProfileActivity.class));
            }
            else if(pageId == R.id.navigation_search) {
                Toast.makeText(this, "Going to webviews", Toast.LENGTH_SHORT).show();
            } else  {
                startActivity(new Intent(BudgetActivity.this, MainActivity.class));
            }

        });
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

