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
public class NewsControllerIntegrationTest {

    @Autowired
    private SecurityTestHelper securityHelper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReturn404OnInvalidNews() throws Exception {
        mockMvc.perform(securityHelper.withUserAuthentication(MockMvcRequestBuilders.get("/news/1337")))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testReturn400CodeErrorResponseOnInvalidJSONFormat() throws Exception {
        String invalidJson = "{";
        mockMvc.perform(
            MockMvcRequestBuilders.post("/news").content(invalidJson).contentType(MediaType.APPLICATION_JSON))

            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.type").value("WARN"))
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void testCreateNewsReturn422CodeErrorResponseOnDTOValidationFailed() throws Exception {
        String jsonWithNulls = "{ \"title\": \"A title\", \"summary\": \"A summary\", \"content\": \"A small content\", \"author\": null}";
        mockMvc.perform(
            MockMvcRequestBuilders.post("/user").content(jsonWithNulls).contentType(MediaType.APPLICATION_JSON))

            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.type").value("WARN"))
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void testGetNewsReturn403CodeErrorResponseOnInsufficientPermissions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/news/1337"))
            .andExpect(MockMvcResultMatchers.status().isForbidden())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.type").value("WARN"))
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

}
