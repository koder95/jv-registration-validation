package core.basesyntax.service;

import core.basesyntax.model.User;

public class UserLoginTooShortException extends RegistrationException {
    public UserLoginTooShortException(User user, int minLength) {
        super("User name \"" + user.getLogin() + "\" must contain min. "
                + minLength + " characters");
    }
}
