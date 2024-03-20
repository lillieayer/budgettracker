package cs445.budgetapp.ui.profile;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import cs445.budgetapp.R;

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

    public void setBudgetStartDate(String date){
        TextView budgetStart = myView.findViewById(R.id.budget_startdate_text);
        budgetStart.setText("Start Date: " + date);
    }

    public void setBudgetEndDate(String date){
        TextView budgetEnd = myView.findViewById(R.id.budget_enddate_text);
        budgetEnd.setText("End Date: " + date);
    }

    public void setBudgetAmount(String amount){
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

