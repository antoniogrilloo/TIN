package it.unimib.tin.TIN.security;

import it.unimib.tin.TIN.controller.RegistrazioneController;
import it.unimib.tin.TIN.exception.AccountException;
import it.unimib.tin.TIN.model.Account;
import it.unimib.tin.TIN.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationFailureHandler authenticationFailureHandler;

    @InjectMocks
    private RegistrazioneController loginController;

    @Autowired
    private AccountRepository arepo;

    @Test
    @Transactional
    @Rollback
    public void testSuccessfulLogin() throws Exception {
        String username = "username";
        String password = "password";

        Account user = new Account();
        user.setUsername(username);
        user.setPassword(password);
        try {
            user.setEmail("s@s.ss");
        } catch (AccountException e) {
            throw new RuntimeException(e);
        }
        arepo.save(user);

        mockMvc.perform(formLogin().loginProcessingUrl("/").user(username).password(password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/protected"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated().withUsername(username));
    }

    @Test
    public void testAccessProtectedEndpointWithValidUser() throws Exception {
        mockMvc.perform(get("/protected").with(user("username").password("password")))
                .andExpect(status().isOk());
    }

    @Test
    public void testFailedLogin() throws Exception {
        String username = "user";
        String password = "wrongpassword";
        UserDetails userDetails = new CustomUserDetails(new Account(username, "a@a.co", "password"));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(authenticationManager.authenticate(authenticationToken)).thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(formLogin().loginProcessingUrl("/").user(username).password(password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?error"));
    }

    @Test
    public void testFailedLoginWithInvalidPassword() throws Exception {
        mockMvc.perform(formLogin().loginProcessingUrl("/").user("username").password("wrongpassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?error"));
    }

    @Test
    public void testFailedLoginWithUnknownUser() throws Exception {
        mockMvc.perform(formLogin().loginProcessingUrl("/").user("unknownuser").password("password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?error"));
    }

}
