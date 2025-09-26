package core.basesyntax.service;

import core.basesyntax.model.User;

public class UserPasswordTooShortException extends RegistrationException {
    public UserPasswordTooShortException(User user, int minLength) {
        super("Password of user "
                + (user == null || user.getLogin() == null ? null : "\"" + user.getLogin() + "\"")
                + " must contain at least "
                + minLength + " characters");
    }
}
