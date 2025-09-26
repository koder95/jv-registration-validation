package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private final RegistrationServiceImpl service = new RegistrationServiceImpl();

    @Test
    void register_null_notOK() {
        User actual = null;
        assertThrows(IllegalArgumentException.class, () -> service.register(actual));
    }

    @Test
    void register_correct_ok() {
        User expected = new User();
        expected.setAge(20);
        expected.setLogin("user12");
        expected.setPassword("pass12");
        assertEquals(expected, service.register(expected));
    }

    @Test
    void register_tooYoungUser_notOk() {
        User expected = new User();
        expected.setAge(16);
        expected.setLogin("user1234");
        expected.setPassword("password1234");
        assertThrows(TooYoungUserException.class, () -> service.register(expected));
    }

    @Test
    void register_negativeUserAge_notOk() {
        User expected = new User();
        expected.setLogin("user1234");
        expected.setPassword("password1234");
        expected.setAge(-1);
        assertThrows(TooYoungUserException.class, () -> service.register(expected));
    }

    @Test
    void register_zeroUserAge_notOk() {
        User expected = new User();
        expected.setLogin("user1234");
        expected.setPassword("password1234");
        expected.setAge(0);
        assertThrows(TooYoungUserException.class, () -> service.register(expected));
    }

    @Test
    void register_userLoginTooShort_notOk() {
        User expected = new User();
        expected.setAge(20);
        expected.setLogin("user1");
        expected.setPassword("password1234");
        assertThrows(UserLoginTooShortException.class, () -> service.register(expected));
    }

    @Test
    void register_emptyUserLogin_notOk() {
        User expected = new User();
        expected.setAge(20);
        expected.setPassword("password1234");
        expected.setLogin("");
        assertThrows(UserLoginTooShortException.class, () -> service.register(expected));
    }

    @Test
    void register_userPasswordTooShort_notOk() {
        User expected = new User();
        expected.setAge(20);
        expected.setLogin("user1234");
        expected.setPassword("pass1");
        assertThrows(UserPasswordTooShortException.class, () -> service.register(expected));
    }

    @Test
    void register_emptyUserPassword_notOk() {
        User expected = new User();
        expected.setAge(20);
        expected.setLogin("user1234");
        expected.setPassword("");
        assertThrows(UserPasswordTooShortException.class, () -> service.register(expected));
    }

    @Test
    void register_theSameNameUsers_notOk() {
        User first = new User();
        first.setAge(20);
        first.setLogin("user1234");
        first.setPassword("password1234");
        Storage.people.add(first);
        User expected = new User();
        expected.setAge(26);
        expected.setLogin("user1234");
        expected.setPassword("password5678");
        assertThrows(RegisteredUserException.class, () -> service.register(expected));
    }

    @AfterEach
    void clearRegister() {
        service.unregisterAll();
    }
}
