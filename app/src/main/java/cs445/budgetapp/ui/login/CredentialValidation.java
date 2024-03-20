package cs445.budgetapp.ui.login;

import android.util.Patterns;

public class CredentialValidation {


    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 32;
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+";
    private static final String DIGITS = "0123456789";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public boolean isPasswordValid(String password) {
        if ((password.length() < MIN_LENGTH) || (password.length() > MAX_LENGTH )) {
            return false;
        }
        boolean hasSpecialChar = false;
        boolean hasDigit = false;
        boolean hasUpper = false;

        for (char c : password.toCharArray()) {
            if (SPECIAL_CHARS.indexOf(c) != -1) {
                hasSpecialChar = true;
            }
            if (DIGITS.indexOf(c) != -1) {
                hasDigit = true;
            }
            if (UPPER.indexOf(c) != -1) {
                hasUpper = true;
            }
        }

        return (hasSpecialChar && hasDigit && hasUpper);

    }

    public boolean isEmailValid(String username) {
        if (username == null) {
            return false;
        }
        if ((username.contains("@")) && (username.length()< 320)) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }
}
