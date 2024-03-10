package cs445.budgetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import cs445.budgetapp.ui.budget.BudgetActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.main_activity_container);

        // Perform item selected listener
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.navigation_budget:
                        startActivity(new Intent(getApplicationContext(), BudgetActivity.class));
                        break;
                    case R.id.navigation_expenses:
                        Toast.makeText(getApplicationContext(), "Going to expense tracker!", Toast.LENGTH_SHORT);
                        break;
                    case R.id.navigation_search:
                        Toast.makeText(getApplicationContext(), "Going to webview!", Toast.LENGTH_SHORT);
                        break;
                    default:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        });





    }


}