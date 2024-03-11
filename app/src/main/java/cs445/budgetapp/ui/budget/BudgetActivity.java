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


import java.util.ArrayList;
import java.util.List;

import cs445.budgetapp.R;

public class BudgetActivity extends AppCompatActivity {

    EditText editName, editDate, editAmt, editComment;
    Button saveButton;
    Spinner categorySpinner;
    String budgetCategory;

    List<Budget> budgetList;

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

        budgetList = new ArrayList<>();
        // add new budgets to old list then reset in shared pref

        BudgetAdapter budgetAdapter = new BudgetAdapter(budgetList);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(budgetAdapter);

    }

    public void onResume(){
        super.onResume();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        editAmt.addTextChangedListener(textWatcher);
        editName.addTextChangedListener(textWatcher);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get user input
                String budgetName = editName.getText().toString();
                Editable budgetAmt = editAmt.getText();
                String budgetDate = editDate.getText().toString();
                String budgetComment = editComment.getText().toString();
                Budget new_budget = new Budget(budgetName, budgetCategory, budgetDate, Integer.parseInt(String.valueOf(budgetAmt)),budgetComment);
                budgetList.add(new_budget);
                // clear fields
                editName.setText("");
                editAmt.setText("0.0");
                editDate.setText("");
                editComment.setText("");

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

    }

    private void checkFields() {
        String text1 = editAmt.getText().toString();
        String text2 = editName.getText().toString();

        if (!text1.isEmpty() && !text2.isEmpty()) {
            saveButton.setEnabled(true);
        } else {
            saveButton.setEnabled(false);
        }
    }

}