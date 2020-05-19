package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.util.SecurityTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class EventControllerIntegrationTest {

    @Autowired
    private SecurityTestHelper securityHelper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReturn404OnInvalidEvent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/event/1337"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testReturnDetailsOnExistingEvent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/event/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("title").isNotEmpty())
            .andExpect(jsonPath("category").isNotEmpty())
            .andExpect(jsonPath("duration").isNotEmpty())
            .andExpect(jsonPath("description").isNotEmpty());
    }
}
