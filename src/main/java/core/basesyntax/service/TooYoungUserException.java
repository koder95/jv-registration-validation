package core.basesyntax.service;

import core.basesyntax.model.User;

public class TooYoungUserException extends RegistrationException {
    public TooYoungUserException(User user, int minAge) {
        super("User "
                + (user == null ? "null" : "\"" + user.getLogin() + "\"")
                + " must be " + minAge + " years old");
    }
}
