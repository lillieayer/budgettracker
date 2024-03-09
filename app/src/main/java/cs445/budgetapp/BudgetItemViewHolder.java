package cs445.budgetapp;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class BudgetItemViewHolder extends RecyclerView.ViewHolder {
    View myView;

    public BudgetItemViewHolder(View view){
        super(view);
        myView = view;
    }

    public void setBudgetName(String name){
        TextView budgetName = myView.findViewById(R.id.budget_name_text);
        budgetName.setText("Name: " + name);
    }

    public void setBudgetDate(String date){
        TextView budgetDate = myView.findViewById(R.id.budget_date_text);
        budgetDate.setText("Date: " + date);
    }

    public void setBudgetAmount(int amount){
        TextView budgetAmt = myView.findViewById(R.id.budget_amount_text);
        budgetAmt.setText("Amount: $" + amount);
    }

    public void setBudgetComment(String comment){
        TextView budgetComment = myView.findViewById(R.id.budget_comment_text);
        budgetComment.setText("Comment: " + comment);
    }

    public void setBudgetCategory(String category){
        TextView budgetCategory = myView.findViewById(R.id.budget_category_text);
        budgetCategory.setText("Category: " + category);
    }

}

