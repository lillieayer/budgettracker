package cs445.budgetapp.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String email;

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
        this.email = email;
    }

    String getDisplayName() {
        return displayName;
    }

    String getEmail(){
        return email;
    }
}