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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private SecurityTestHelper securityHelper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReturn400CodeErrorResponseOnInvalidJSONFormat() throws Exception {
        String invalidJson = "{";
        mockMvc.perform(
            MockMvcRequestBuilders.post("/user").content(invalidJson).contentType(MediaType.APPLICATION_JSON))

            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.type").value("WARN"))
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void testReturn422CodeErrorResponseOnDTOValidationFailed() throws Exception {
        String jsonWithNulls = "{ \"firstname\": \"John\", \"lastname\": \"Doe\", \"address\": null, \"login\": null}";
        mockMvc.perform(
            MockMvcRequestBuilders.post("/user").content(jsonWithNulls).contentType(MediaType.APPLICATION_JSON))

            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.type").value("WARN"))
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void testReturn403CodeErrorResponseOnNoTokenProvided() throws Exception {
        String changePasswordJson = "{ \"email\": \"john@example.com\", \"password\": \"12345678\"}";

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/user/4").content(changePasswordJson).contentType(MediaType.APPLICATION_JSON))

            .andExpect(MockMvcResultMatchers.status().isForbidden())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.type").value("WARN"))
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void testReturn403CodeErrorResponseOnInsufficientPermissions() throws Exception {
        String changePasswordJson = "{ \"email\": \"john@example.com\", \"password\": \"12345678\"}";
        mockMvc.perform(securityHelper.withUserAuthentication(MockMvcRequestBuilders.patch("/user/4")
            .content(changePasswordJson)
            .contentType(MediaType.APPLICATION_JSON)))

            .andExpect(MockMvcResultMatchers.status().isForbidden())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.type").value("WARN"))
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void testGetUsersReturn403CodeErrorResponseOnInsufficientPermissions() throws Exception {
        mockMvc.perform(securityHelper.withUserAuthentication(MockMvcRequestBuilders.get("/user")
            .contentType(MediaType.APPLICATION_JSON)))

            .andExpect(MockMvcResultMatchers.status().isForbidden())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.type").value("WARN"))
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void testGetUsersWithPermissions() throws Exception {
        mockMvc.perform(securityHelper.withAdminAuthentication(MockMvcRequestBuilders.get("/user")
            .contentType(MediaType.APPLICATION_JSON)))

            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.header().exists("X-Total-Count"))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUnlockUserWithInsufficientPermissions() throws Exception {
        mockMvc.perform(securityHelper.withUserAuthentication(MockMvcRequestBuilders.patch("/user/2/unlock")
            .contentType(MediaType.APPLICATION_JSON)))

            .andExpect(MockMvcResultMatchers.status().isForbidden())
            .andExpect(jsonPath("$.type").value("WARN"))
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void testUnlockUserWithAdminPermissions() throws Exception {
        mockMvc.perform(securityHelper.withAdminAuthentication(MockMvcRequestBuilders.patch("/user/2/unlock")
            .contentType(MediaType.APPLICATION_JSON)))

            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
