package cs445.budgetapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;

public class AddBudget extends Fragment {

    String categoryChosen;
    String budgetName;
    int budgetAmt;
    String budgetComment;
    String budgetDate;


    public AddBudget() {
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
        View myView = inflater.inflate(R.layout.fragment_add_budget, container, false);

        // Find UI elements
        EditText editName = myView.findViewById(R.id.edit_budget_name);
        EditText editDate = myView.findViewById(R.id.edit_budget_date);
        EditText editAmt = myView.findViewById(R.id.edit_budget_amt);
        EditText editComment = myView.findViewById(R.id.edit_budget_comment);
        // get user input
        budgetName = editName.getText().toString();
        budgetAmt = Integer.parseInt(editAmt.getText().toString());
        budgetDate = editDate.getText().toString();
        budgetComment = editComment.getText().toString();

        // save user chosen category

        Spinner budgetCategory = myView.findViewById(R.id.edit_budget_category);
        budgetCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryChosen = adapterView.getItemAtPosition(i).toString();
                Log.v("Success", "Category chosen!");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button saveButton = myView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pass data to bundle
                Bundle bundle = new Bundle();
                bundle.putString("budgetCategory", categoryChosen);
                bundle.putString("budgetName", budgetName);
                bundle.putInt("budgetAmt", budgetAmt);
                bundle.putString("budgetComment", budgetComment);
                bundle.putString("budgetDate", budgetDate);
                Log.v("Success", "Saving user data and going to budget list");

                // navigate to next fragment
                BudgetFragment budgetFragment = new BudgetFragment();
                budgetFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.edit_budget_layout, budgetFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });



        return myView;
    }
}