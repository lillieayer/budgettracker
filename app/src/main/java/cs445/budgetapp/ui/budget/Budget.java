package cs445.budgetapp.ui.budget;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.Date;

import cs445.budgetapp.database.User;

/**
 * Budget data class object will define the terms for a budget item set by user
 */
@Entity(tableName = "budgets",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE))
public class Budget {
    private String name;

    private String category;
    private String date;
    private int amount;
    private String comment;

    public Budget(String name, String category, String date, int amount, String comment){
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
    }

    public Budget(String name, String category, String date, int amount){
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
    }

    public Budget(String name, String category, int amount){
        this.name = name;
        this.category = category;
        this.amount = amount;
    }

    public String getName(){
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDate(){
        return date;
    }

    public int getAmount(){
        return amount;
    }

    public String getComment(){
        return comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
