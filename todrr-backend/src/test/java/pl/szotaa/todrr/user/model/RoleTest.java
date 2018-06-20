package pl.szotaa.todrr.user.model;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class RoleTest {

    @Test
    public void getAuthority_roleNameReturned() throws Exception {
        //given
        Role userRole = Role.ROLE_USER;
        Role adminRole = Role.ROLE_ADMIN;

        //when&then
        assertEquals("ROLE_USER", userRole.getAuthority());
        assertEquals("ROLE_ADMIN", adminRole.getAuthority());
    }
}