package cs445.budgetapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import cs445.budgetapp.ui.budget.Budget;
import cs445.budgetapp.ui.budget.BudgetActivity;

public class ProfileActivity extends AppCompatActivity {

    private MyApplication app;

    private DatabaseReference userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        List<Budget> budgetList = new ArrayList<>();
        BudgetAdapter budgetAdapter = new BudgetAdapter(budgetList);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(budgetAdapter);

        app = (MyApplication) getApplication();
        // get user to access auth email
        FirebaseUser currUser = app.getAuthUser();

        String[] userPathArr = currUser.getEmail().split("@");
        String userPath = userPathArr[0];
        // initialize db reference for access
        userData = app.getDb().getReference("Users/"+ userPath);

        // retrieve users past budgets
        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot pastBudgets) {
                if (pastBudgets.exists()){
                    ArrayList<Budget> temp = new ArrayList<>();
                    int numBudgets = 0;

                    for (DataSnapshot budget : pastBudgets.getChildren()){
                        Budget old_budget = budget.getValue(Budget.class);
                        temp.add(old_budget);
                        numBudgets++;
                    }
                    budgetList.addAll(temp);
                    budgetAdapter.notifyItemRangeInserted(0,numBudgets);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot budgets, @Nullable String previousChildName) {
                Budget new_budget = (Budget)budgets.getValue(Budget.class);
                if (new_budget != null){
                    budgetList.add(new_budget);
                    int i = budgetList.indexOf(new_budget);
                    budgetAdapter.notifyItemInserted(i);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //not modifying data
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Budget old_budget = (Budget)snapshot.getValue(Budget.class);
                int i = budgetList.indexOf(old_budget);
                budgetList.remove(old_budget);
                budgetAdapter.notifyItemRemoved(i);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //ignore
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //ignore
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_profile);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.budget_activity_container);
        // Perform item selected listener
        bottomNavigationView.setOnItemReselectedListener(item -> {
            int pageId = item.getItemId();

            if(pageId == R.id.navigation_budget){
                startActivity(new Intent(ProfileActivity.this, BudgetActivity.class));
            }
            else if(pageId == R.id.navigation_profile) {
                Toast.makeText(this, "Already on profile page", Toast.LENGTH_SHORT).show();
            }
            else if(pageId == R.id.navigation_search) {
                Toast.makeText(this, "Going to webviews", Toast.LENGTH_SHORT).show();
            } else  {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userData = null;
        app = null;
    }


}