package dream.store;

import dream.model.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PsqlStoreTest {

    @Test
    public void testFindUserByEmail() {
        Store mstore = mock(Store.class);
        String email = "test@mail.ru";
        User user = new User(1, "Test", "test@mail.ru", "password");

        when(mstore.findUserByEmail(email)).thenReturn(user);
        assertThat(user, is(mstore.findUserByEmail(email)));
    }
}