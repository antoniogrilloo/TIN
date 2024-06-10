package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.controller.AdminController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminController adminController;

    @Test
    public void testSuccessfulResearch() throws Exception {
        mockMvc.perform(get("/search")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void unsuccessfulCategory() throws Exception {
        adminController.inserisciCategoria("ww");
        adminController.aggiungiCategoria();
        mockMvc.perform(formLogin().loginProcessingUrl("/").user("admin").password("admin"))
                .andDo(result -> get("/protected/admin/aggiungiCategoria"))
                .andExpect(status().is3xxRedirection())
                .andExpect(SecurityMockMvcResultMatchers.authenticated().withUsername("admin"));
        mockMvc.perform(post("/protected/admin/inserisciCategoria")).andExpect(status().is4xxClientError());
    }
}
