package core.basesyntax.service;

import core.basesyntax.model.User;

public class RegisteredUserException extends RegistrationException {
    public RegisteredUserException(User user) {
        super("User "
                + (user == null ? "null" : "\"" + user.getLogin() + "\"")
                + " have been registered");
    }
}
