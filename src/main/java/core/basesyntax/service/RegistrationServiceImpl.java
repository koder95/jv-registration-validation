package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int DEFAULT_LOGIN_MIN_LENGTH = 6;
    public static final int DEFAULT_PASSWORD_MIN_LENGTH = 6;
    public static final int DEFAULT_MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();
    private final int loginMinLength;
    private final int passwordMinLength;
    private final int minUserAge;

    public RegistrationServiceImpl(int loginMinLength, int passwordMinLength, int minUserAge) {
        this.loginMinLength = loginMinLength;
        this.passwordMinLength = passwordMinLength;
        this.minUserAge = minUserAge;
    }

    public RegistrationServiceImpl() {
        this(DEFAULT_LOGIN_MIN_LENGTH, DEFAULT_PASSWORD_MIN_LENGTH, DEFAULT_MIN_USER_AGE);
    }

    public int getLoginMinLength() {
        return loginMinLength;
    }

    public int getPasswordMinLength() {
        return passwordMinLength;
    }

    public int getMinUserAge() {
        return minUserAge;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegisteredUserException(user);
        }
        if (user.getLogin().length() < loginMinLength) {
            throw new UserLoginTooShortException(user, loginMinLength);
        }
        if (user.getPassword().length() < passwordMinLength) {
            throw new UserPasswordTooShortException(user, passwordMinLength);
        }
        if (user.getAge() < minUserAge) {
            throw new TooYoungUserException(user, minUserAge);
        }
        return storageDao.add(user);
    }

    public void unregisterAll() {
        storageDao.removeAll();
    }
}
