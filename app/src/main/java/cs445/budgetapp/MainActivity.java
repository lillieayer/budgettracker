package cs445.budgetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import cs445.budgetapp.ui.budget.BudgetActivity;
import cs445.budgetapp.ui.login.SignUpFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_main);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.main_activity_container);

        // Perform item selected listener

        bottomNavigationView.setOnItemReselectedListener(item -> {
            int pageId = item.getItemId();

            if(pageId == R.id.navigation_budget){
                Toast.makeText(this, "Going to budget", Toast.LENGTH_SHORT).show();
                startActivity( new Intent(MainActivity.this, BudgetActivity.class));
            }
            else if(pageId == R.id.navigation_expenses) {
                Toast.makeText(this, "Going to expense tracker!", Toast.LENGTH_SHORT).show();
            }
            else if(pageId == R.id.navigation_search) {
                Toast.makeText(this, "Going to webviews", Toast.LENGTH_SHORT).show();
            } else  {
                Toast.makeText(this, "Already on Home Page!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}