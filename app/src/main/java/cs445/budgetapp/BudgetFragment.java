package cs445.budgetapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class BudgetFragment extends Fragment {



    public BudgetFragment() {
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
        View myView = inflater.inflate(R.layout.fragment_budget, container, false);
        RecyclerView recyclerView=myView.findViewById(R.id.recycler_view);

        List<Budget> budgetList = new ArrayList<>();
        // add new budgets to old list then reset in shared pref
        BudgetAdapter budgetAdapter = new BudgetAdapter(budgetList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(budgetAdapter);

        FloatingActionButton addBudget = myView.findViewById(R.id.floatingActionButton);
        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Success!", "Adding New budget!");
            }
        });


        return myView;

    }

}