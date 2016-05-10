package com.group1.eda_397_group1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Max on 10.05.2016.
 */
public class SingletonTest {

    @Test
    public void singleton_works_correctly() throws NullPointerException {
        UserSingleton user = UserSingleton.getInstance();
        assertNotEquals(null, user);
        assertEquals(null, user.getEmail());
        user.setEmail("testuser@testdomain.com");
        assertEquals("testuser@testdomain.com", user.getEmail());
    }
}
