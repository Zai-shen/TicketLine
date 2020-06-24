package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.util.SecurityTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class NewsControllerIntegrationTest {

    @Autowired
    private SecurityTestHelper securityHelper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReturn404OnInvalidNews() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/news/1337"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetNewsListHasTotalCountHeader() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/news"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.header().exists("X-Total-Count"));
    }
}
