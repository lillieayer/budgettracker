package cs445.budgetapp.ui.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import cs445.budgetapp.MyApplication;
import cs445.budgetapp.R;

public class BudgetActivity extends AppCompatActivity {

    EditText editName, editDate, editAmt, editComment;
    Button saveButton;
    Spinner categorySpinner;
    String budgetCategory, budgetName;
    List<Budget> budgetList;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("userEmail");

        categorySpinner = findViewById(R.id.edit_budget_category);
        saveButton = findViewById(R.id.saveButton);
        // Find UI elements
        editName = findViewById(R.id.edit_budget_name);
        editDate = findViewById(R.id.edit_budget_date);
        editAmt = findViewById(R.id.edit_budget_amt);
        editComment = findViewById(R.id.edit_budget_comment);


        budgetList = new ArrayList<>();
        // add new budgets to old list then reset in shared pref

        BudgetAdapter budgetAdapter = new BudgetAdapter(budgetList);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(budgetAdapter);

    }

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

        saveButton.setOnClickListener(view -> {
            // get user input
            Editable budgetAmt = editAmt.getText();
            String budgetDate = editDate.getText().toString();
            String budgetComment = editComment.getText().toString();
            Budget new_budget = new Budget(budgetName, budgetCategory, budgetDate, Integer.parseInt(String.valueOf(budgetAmt)),budgetComment);
            budgetList.add(new_budget);

            MyApplication app = new MyApplication();
            DatabaseReference users = app.getDb().getReference("/Users");
            users.child(email).setValue(new_budget);

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


}