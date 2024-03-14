package cs445.budgetapp.ui.budget;


/**
 * Budget data class object will define the terms for a budget item set by user
 */
public class Budget {
    private String name;
    private String category;
    private String date;
    private String amount;
    private String comment;

    public Budget(){
    }

    public Budget(String name, String category, String date, String amount, String comment){
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
    }

    public Budget(String name, String category, String date, String amount){
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
    }

    public Budget(String name, String category, String amount){
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

    public String getAmount(){
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

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
