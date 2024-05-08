package it.unimib.tin.TIN.security;

import it.unimib.tin.TIN.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LogoutTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository arepo;

    @Test
    @Transactional
    @Rollback
    public void testLogout() throws Exception {
        // Effettua il login
        mockMvc.perform(formLogin().loginProcessingUrl("/").user("admin").password("admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/protected"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated().withUsername("admin"));

        // Verifica che l'utente sia effettivamente autenticato
        mockMvc.perform(formLogin().loginProcessingUrl("/").user("admin").password("admin"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated().withUsername("admin"));

        // Effettua il logout
        mockMvc.perform(logout())
                .andExpect(redirectedUrlPattern("/?logout"));

        // Verifica che l'utente non sia più autenticato
        mockMvc.perform(formLogin())
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

    @Test
    @Transactional
    @Rollback
    public void testLogout2() throws Exception {
        // Effettua il login
        mockMvc.perform(formLogin().loginProcessingUrl("/").user("aaa@aaa.com").password("admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/protected"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated().withUsername("admin"));

        // Verifica che l'utente sia effettivamente autenticato
        mockMvc.perform(formLogin().loginProcessingUrl("/").user("aaa@aaa.com").password("admin"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated().withUsername("admin"));

        // Effettua il logout
        mockMvc.perform(logout())
                .andExpect(redirectedUrlPattern("/?logout"));

        // Verifica che l'utente non sia più autenticato
        mockMvc.perform(formLogin())
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}
