package core.basesyntax.service;

import core.basesyntax.model.User;

public class UserLoginTooShortException extends RegistrationException {
    public UserLoginTooShortException(User user, int minLength) {
        super("User name "
                + (user == null || user.getLogin() == null ? null : "\"" + user.getLogin() + "\"")
                + " must contain at least "
                + minLength + " characters");
    }
}
