package cs445.budgetapp.ui.budget;


/**
 * Budget data class object will define the terms for a budget item set by user
 */
public class Budget {
    private String name;
    private String category;
    private String startDate;
    private String endDate;
    private String amount;
    private String comment;

    public Budget(){
    }

    public Budget(String name, String category, String startDate, String endDate, String amount, String comment){
        this.name = name;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.comment = comment;
    }


    public String getName(){
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getStartDate(){
        return startDate;
    }
    public String getEndDate(){return endDate;}

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

    public void setStartDate(String date) {
        this.startDate = date;
    }

    public void setEndDate(String date) {
        this.endDate = date;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
