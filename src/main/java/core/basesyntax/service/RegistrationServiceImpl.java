package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IllegalArgumentException(new NullPointerException());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegisteredUserException(user);
        }
        if (user.getLogin().length() < 6) {
            throw new UserLoginTooShortException(user, 6);
        }
        if (user.getPassword().length() < 6) {
            throw new UserPasswordTooShortException(user, 6);
        }
        if (user.getAge() < 18) {
            throw new TooYoungUserException(user, 18);
        }
        return storageDao.add(user);
    }

    public void unregisterAll() {
        storageDao.removeAll();
    }
}
