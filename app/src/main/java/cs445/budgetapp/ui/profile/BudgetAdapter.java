package cs445.budgetapp.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cs445.budgetapp.R;
import cs445.budgetapp.ui.budget.Budget;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetItemViewHolder> {
    private List<Budget> list;

    public BudgetAdapter(List<Budget> budgetList) {
        this.list = budgetList;
    }

    @Override
    public BudgetItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_item_layout, parent, false);
        return new BudgetItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BudgetItemViewHolder holder, int i) {
        Budget budget = list.get(i);
        holder.setBudgetName(budget.getName());
        holder.setBudgetAmount(budget.getAmount());
        holder.setBudgetDate(budget.getDate());
        holder.setBudgetCategory(budget.getCategory());
        holder.setBudgetComment(budget.getComment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
