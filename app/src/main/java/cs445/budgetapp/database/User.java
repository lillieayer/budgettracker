package cs445.budgetapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

import cs445.budgetapp.ui.budget.Budget;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    public int uid;

    public String name;
    public String email;
}


