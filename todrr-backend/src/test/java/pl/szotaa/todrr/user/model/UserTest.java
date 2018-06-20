package pl.szotaa.todrr.user.model;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {

    @Test
    public void equals_equalUsers_trueReturned() throws Exception {
        //given
        User user = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        User equalUser = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        //when&then
        assertTrue(user.equals(equalUser));
    }

    @Test
    public void equals_notEqualUsers_falseReturned() throws Exception {
        //given
        User user = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        User user2 = User.builder()
                .username("username2")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        //when&then
        assertFalse(user.equals(user2));
    }

    @Test
    public void equals_methodIsReflexive() throws Exception {
        //given
        User user = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        //when&then
        assertTrue(user.equals(user));
    }

    @Test
    public void equals_methodIsSymmetric() throws Exception {
        //given
        User user = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        User equalUser = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        //when&then
        assertTrue(user.equals(equalUser) && equalUser.equals(user));
    }

    @Test
    public void equals_methodIsTransitive() throws Exception {
        //given
        User user = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        User equalUser = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        User equalUser2 = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        //when&then
        if(user.equals(equalUser) || equalUser.equals(equalUser2)){
            assertTrue(user.equals(equalUser2));
        }
    }

    @Test
    public void hashCode_equalUsersHaveEqualHashcodes(){
        //given
        User user = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        User equalUser = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        //when&then
        assertEquals(user.hashCode(), equalUser.hashCode());
    }

    @Test
    public void hashCode_notEqualUsersDontHaveEqualHashcodes(){
        //given
        User user = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        User user2 = User.builder()
                .username("username2")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        //when&then
        assertNotEquals(user.hashCode(), user2.hashCode());
    }
}