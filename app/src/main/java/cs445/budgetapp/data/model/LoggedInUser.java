package cs445.budgetapp.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String email;
    private String displayName;

    public LoggedInUser(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
    }

    public String getUserEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }
}