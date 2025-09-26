package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private final RegistrationServiceImpl service = new RegistrationServiceImpl();
    private final Random random = new Random();

    private String randomLetterString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append((char) random.nextInt());
        }
        return builder.toString();
    }

    @Test
    void register_null_notOK() {
        assertThrows(RegistrationException.class, () -> service.register(null));
    }

    @Test
    void register_correct_ok() {
        User expected = new User();
        expected.setAge(service.getMinUserAge());
        expected.setLogin(randomLetterString(service.getLoginMinLength()));
        expected.setPassword(randomLetterString(service.getPasswordMinLength()));
        assertEquals(expected, service.register(expected));
    }

    @Test
    void register_tooYoungUser_notOk() {
        User expected = new User();
        expected.setAge(service.getMinUserAge() - 1);
        expected.setLogin(randomLetterString(service.getLoginMinLength()));
        expected.setPassword(randomLetterString(service.getPasswordMinLength()));
        assertThrows(TooYoungUserException.class, () -> service.register(expected));
    }

    @Test
    void register_negativeUserAge_notOk() {
        User expected = new User();
        expected.setLogin(randomLetterString(service.getLoginMinLength()));
        expected.setPassword(randomLetterString(service.getPasswordMinLength()));
        expected.setAge(-1);
        assertThrows(TooYoungUserException.class, () -> service.register(expected));
    }

    @Test
    void register_zeroUserAge_notOk() {
        User expected = new User();
        expected.setLogin(randomLetterString(service.getLoginMinLength()));
        expected.setPassword(randomLetterString(service.getPasswordMinLength()));
        expected.setAge(0);
        assertThrows(TooYoungUserException.class, () -> service.register(expected));
    }

    @Test
    void register_userLoginTooShort_notOk() {
        User expected = new User();
        expected.setAge(20);
        expected.setLogin(randomLetterString(service.getLoginMinLength() - 1));
        expected.setPassword(randomLetterString(service.getPasswordMinLength()));
        assertThrows(UserLoginTooShortException.class, () -> service.register(expected));
    }

    @Test
    void register_emptyUserLogin_notOk() {
        User expected = new User();
        expected.setAge(20);
        expected.setLogin("");
        expected.setPassword(randomLetterString(service.getPasswordMinLength()));
        assertThrows(UserLoginTooShortException.class, () -> service.register(expected));
    }

    @Test
    void register_userPasswordTooShort_notOk() {
        User expected = new User();
        expected.setAge(20);
        expected.setLogin(randomLetterString(service.getLoginMinLength()));
        expected.setPassword(randomLetterString(service.getPasswordMinLength() - 1));
        assertThrows(UserPasswordTooShortException.class, () -> service.register(expected));
    }

    @Test
    void register_emptyUserPassword_notOk() {
        User expected = new User();
        expected.setAge(20);
        expected.setLogin(randomLetterString(service.getLoginMinLength()));
        expected.setPassword("");
        assertThrows(UserPasswordTooShortException.class, () -> service.register(expected));
    }

    @Test
    void register_theSameNameUsers_notOk() {
        String theSameName = randomLetterString(service.getLoginMinLength());
        User first = new User();
        first.setAge(20);
        first.setLogin(theSameName);
        first.setPassword(randomLetterString(service.getPasswordMinLength()));
        Storage.people.add(first);
        User expected = new User();
        expected.setAge(26);
        expected.setLogin(theSameName);
        expected.setPassword(randomLetterString(service.getPasswordMinLength()));
        assertThrows(RegisteredUserException.class, () -> service.register(expected));
    }

    @AfterEach
    void clearRegister() {
        service.unregisterAll();
    }
}
