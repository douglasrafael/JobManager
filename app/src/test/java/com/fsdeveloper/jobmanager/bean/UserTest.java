package com.fsdeveloper.jobmanager.bean;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Created by Douglas Rafael on 21/04/2016.
 * @version 1.0
 */
public class UserTest {
    private User user;
    private static final int ID = 1516;
    private static final String NAME = "Terry J. Clemons";
    private static final String EMAIL = "clemons@mail.com";
    private static final String PASSWORD = "156849";

    @Before
    public void setUp() throws Exception {
        user = new User(ID, NAME, EMAIL, PASSWORD, null);
    }

    @Test
    public void testGetId() throws Exception {
        assertFalse(user.getId() == 1);
        assertTrue(user.getId() == ID);
    }

    @Test
    public void testSetId() throws Exception {
        user.setId(1);
        assertTrue(user.getId() == 1);

        user.setId(7518919);
        assertFalse(user.getId() == Integer.parseInt("156"));
        assertTrue(user.getId() != 1);
        assertTrue(user.getId() == 7518919);
    }

    @Test
    public void testGetName() throws Exception {
        assertTrue(user.getName().equals(NAME));
    }

    @Test
    public void testSetName() throws Exception {
        user.setName("Jamie S. Lieber");
        assertTrue(user.getName().equals("Jamie S. Lieber"));
        assertTrue(!user.getName().equals("Jamie"));
        assertFalse(user.getName().equals(""));
        assertFalse(user.getName().equals(null));
    }

    @Test
    public void testGetEmail() throws Exception {
        assertFalse(user.getName().equals("not@mail.com"));
        assertTrue(user.getEmail().equals(EMAIL));
    }

    @Test
    public void testSetEmail() throws Exception {
        user.setEmail("jami_md@outlook.com");
        assertFalse(user.getEmail().equals(EMAIL));
        assertTrue(user.getEmail().equals("jami_md@outlook.com"));
    }

    @Test
    public void testGetPassword() throws Exception {
        assertFalse(user.getPassword().equals(""));
        assertTrue(user.getPassword().equals(PASSWORD));
    }

    @Test
    public void testSetPassword() throws Exception {
        user.setPassword("156");
        assertTrue(!user.getPassword().equals(PASSWORD));
        assertTrue(user.getPassword().equals("156"));
    }

    @Test
    public void testToString() throws Exception {
        String result = "User{" +
                "id=" + ID +
                ", name='" + NAME + '\'' +
                ", email='" + EMAIL + '\'' +
                ", password='" + PASSWORD + '\'' +
                ", created_at='" + null + '\'' +
                ", last_login='" + null + '\'' + "}";

        assertTrue(user.toString().equals(result));
    }

    @Test
    public void testEquals() throws Exception {
        User user_two = new User(26, "Maxx", "max@mail.com", null, null);
        assertFalse(user.equals(user_two));
        assertTrue(user_two.equals(user_two));
        assertTrue(user.equals(user));
    }
}