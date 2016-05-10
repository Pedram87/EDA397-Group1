package com.group1.eda_397_group1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Max on 10.05.2016.
 */
public class SingletonTest {

    UserSingleton superuser;
    @Test
    public void singleton_creation_worksCorrect() throws NullPointerException, Exception {
        UserSingleton user = UserSingleton.getInstance();
        assertNotNull(user);
        assertEquals(null, user.getEmail());
    }

    @Test
    public void singleton_setEmail_worksCorrect() throws Exception {
        UserSingleton user = UserSingleton.getInstance();
        user.setEmail("testuser@testdomain.com");
        assertEquals("testuser@testdomain.com", user.getEmail());
    }

    private void setSuperuser() {
        superuser = UserSingleton.getInstance();
        superuser.setEmail("1@1.com");
    }

    @Test
    public void singleton_staysCorrect() throws Exception {
        setSuperuser();
        superuser = UserSingleton.getInstance();
        assertEquals("1@1.com", superuser.getEmail());
    }
}
