package RoundTrip.Controller;

import RoundTrip.SupportClasses.RegistrationRequest;
import RoundTrip.controller.LoginController;
import RoundTrip.model.LoginRequest;
import RoundTrip.model.Users;
import RoundTrip.repository.AdminRepository;
import RoundTrip.repository.EditorRepository;
import RoundTrip.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private EditorRepository editorRepository;

    @MockBean
    private UsersRepository usersRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Users user1;

    @BeforeEach
    public void setUp() {
        user1 = new Users();
        user1.setId(1L);
        user1.setEmail("user1@example.com");
        user1.setPassword("user1111");
        user1.setUsername("User1");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(usersRepository.findByEmail("user1@example.com")).thenReturn(user1);
    }

    @Test
    public void testRegister() throws Exception {
        // Registration
        String userType = "User";
        String username = "User1";
        String email= "user1@example.com";
        String password= "user1111";
        RegistrationRequest registrationRequest = new RegistrationRequest(userType, email, password, username);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin() throws Exception {
        setUp();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user1@example.com");
        loginRequest.setPassword("user1111");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userType").value("User"));

    }

    @Test
    public void testInvalidCredentials() throws Exception {
        // Attempt to login with invalid credentials
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nonexistent@example.com");
        loginRequest.setPassword("invalidpassword");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}
