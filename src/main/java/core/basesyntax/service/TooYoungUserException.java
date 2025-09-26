package core.basesyntax.service;

import core.basesyntax.model.User;

public class TooYoungUserException extends RegistrationException {
    public TooYoungUserException(User user, int minAge) {
        super("User "
                + (user == null || user.getLogin() == null ? null : "\"" + user.getLogin() + "\"")
                + " must be at least " + minAge + " years old");
    }
}
