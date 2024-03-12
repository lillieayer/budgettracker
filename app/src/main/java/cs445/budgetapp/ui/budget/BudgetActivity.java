package cs445.budgetapp.ui.budget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import cs445.budgetapp.MyApplication;
import cs445.budgetapp.R;
import cs445.budgetapp.data.model.LoggedInUser;

public class BudgetActivity extends AppCompatActivity {

    EditText editName, editDate, editAmt, editComment;
    Button saveButton;
    Spinner categorySpinner;
    String budgetCategory, budgetName;
    List<Budget> budgetList;
    String email;
    private MyApplication app;

    private DatabaseReference userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        categorySpinner = findViewById(R.id.edit_budget_category);
        saveButton = findViewById(R.id.saveButton);
        // Find UI elements
        editName = findViewById(R.id.edit_budget_name);
        editDate = findViewById(R.id.edit_budget_date);
        editAmt = findViewById(R.id.edit_budget_amt);
        editComment = findViewById(R.id.edit_budget_comment);
        // initialzie app ref
        app = new MyApplication();
        // get user to access auth email
        FirebaseUser currUser = app.getAuthUser();
        if (currUser != null) {
            email = currUser.getEmail();
            // initialize db reference for access
            userData = app.getDb().getReference("/Users").child(email);
            // generate unique key for budget storage

            BudgetAdapter budgetAdapter = new BudgetAdapter(budgetList);
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(budgetAdapter);

        }

        saveButton.setOnClickListener(view -> {
            // get user input
            Editable budgetAmt = editAmt.getText();
            String budgetDate = editDate.getText().toString();
            String budgetComment = editComment.getText().toString();
            Budget new_budget = new Budget(budgetName, budgetCategory, budgetDate, Integer.parseInt(String.valueOf(budgetAmt)),budgetComment);

            // make unique key for each budget's storage
            String key = userData.push().getKey();
            userData.child(key).setValue(new_budget);

            // clear fields
            editName.setText("");
            editAmt.setText("0.0");
            editDate.setText("");
            editComment.setText("");

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
    }

    @Override
    public void onResume(){
    super.onResume();

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
            if (!budgetName.isEmpty()){
                saveButton.setEnabled(true);
            } else{
                saveButton.setEnabled(false);
            }

        }
    });


        userData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Budget new_budget = snapshot.getValue(Budget.class);
                budgetList.add(new_budget);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Budget old_budget = snapshot.getValue(Budget.class);
                budgetList.remove(old_budget);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

