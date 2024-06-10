package it.unimib.tin.TIN.repository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.unimib.tin.TIN.model.Account;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class PasswordChangeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    @Transactional
    @Rollback
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        Account account = new Account();
        account.setUsername("username");
        account.setPassword("oldPass123");
        accountRepository.save(account);
    }

    @Test
    @Transactional
    @Rollback
    public void testChangePasswordSuccess() throws Exception {
        mockMvc.perform(post("/protected/user/cambiaPassword/modifica")
                        .with(user("username").password("oldPass123").roles("USER"))
                        .with(csrf())
                        .param("old", "oldPass123")
                        .param("nuova", "newPass123")
                        .param("nuova_check", "newPass123"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Transactional
    @Rollback
    public void testChangePasswordMismatchError() throws Exception {
        mockMvc.perform(post("/protected/user/cambiaPassword/modifica")
                        .with(user("username").password("oldPass123").roles("USER"))
                        .with(csrf())
                        .param("old", "oldPass123")
                        .param("nuova", "newPass123")
                        .param("nuova_check", "differentNewPass123"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Transactional
    @Rollback
    public void testChangePasswordOldPasswordIncorrectError() throws Exception {
        mockMvc.perform(post("/protected/user/cambiaPassword/modifica")
                        .with(user("username").password("oldPass123").roles("USER"))
                        .with(csrf())
                        .param("old", "oldwrongPass123")
                        .param("nuova", "newPass123")
                        .param("nuova_check", "newPass123"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Transactional
    @Rollback
    public void testChangePasswordUserNotFoundError() throws Exception {
        mockMvc.perform(post("/protected/user/cambiaPassword/modifica")
                        .with(user("nonexistentUser").password("password").roles("USER"))
                        .with(csrf())
                        .param("old", "oldPass123")
                        .param("nuova", "newPass123")
                        .param("nuova_check", "newPass123"))
                .andExpect(status().is3xxRedirection());
    }
}
