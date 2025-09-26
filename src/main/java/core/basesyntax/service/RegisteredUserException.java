package core.basesyntax.service;

import core.basesyntax.model.User;

public class RegisteredUserException extends RegistrationException {
    public RegisteredUserException(User user) {
        super("User "
                + (user == null || user.getLogin() == null ? null : "\"" + user.getLogin() + "\"")
                + " is already registered");
    }
}
