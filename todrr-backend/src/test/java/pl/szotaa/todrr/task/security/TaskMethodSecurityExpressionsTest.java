package pl.szotaa.todrr.task.security;

import java.util.Collection;
import javax.security.auth.Subject;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import pl.szotaa.todrr.task.model.Task;
import pl.szotaa.todrr.task.service.TaskService;
import pl.szotaa.todrr.user.model.User;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskMethodSecurityExpressionsTest {

    @InjectMocks
    private TaskMethodSecurityExpressions securityExpressions;

    @Mock
    private TaskService taskService;

    @Test
    public void isOwner_ownerRequested_trueReturned() throws Exception {
        //given
        User user = User.builder()
                .email("user@email.com")
                .build();

        Task task = Task.builder()
                .owner(user)
                .build();

        Authentication authentication = new TestAuthenticationObject("user@email.com");


        when(taskService.findById(anyLong())).thenReturn(task);


        //when
        boolean result = securityExpressions.isOwner(1L, authentication);

        //then
        assertTrue(result);
    }

    @Test
    public void isOwner_notAnOwnerRequested_falseReturned() throws Exception {
        //given
        User user = User.builder()
                .email("user@email.com")
                .build();

        Task task = Task.builder()
                .owner(user)
                .build();

        Authentication authentication = new TestAuthenticationObject("anotherUser@email.com");


        when(taskService.findById(anyLong())).thenReturn(task);


        //when
        boolean result = securityExpressions.isOwner(1L, authentication);

        //then
        assertFalse(result);
    }

    /**
     * Mock Authentication object for tests convenience.
     */

    @AllArgsConstructor
    private static class TestAuthenticationObject implements Authentication {

        private String name;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return false;
        }

        @Override
        public void setAuthenticated(boolean b) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public boolean implies(Subject subject) {
            return false;
        }
    }
}