package core.basesyntax.service;

import core.basesyntax.model.User;

public class UserPasswordTooShortException extends RegistrationException {
    public UserPasswordTooShortException(User user, int minLength) {
        super("Password of user \"" + user.getLogin() + "\" must contain min. "
                + minLength + " characters");
    }
}
