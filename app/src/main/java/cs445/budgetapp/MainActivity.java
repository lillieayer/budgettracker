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

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.main_activity_container);

        // Perform item selected listener
        bottomNavigationView.setOnItemReselectedListener(item -> {
            Intent intent;
            switch(item.getItemId())
            {
                case R.id.navigation_budget:
                    intent = new Intent(getApplicationContext(), BudgetActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_expenses:
                    Toast.makeText(getApplicationContext(), "Going to expense tracker!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_search:
                    Toast.makeText(getApplicationContext(), "Going to webview!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });





    }


}