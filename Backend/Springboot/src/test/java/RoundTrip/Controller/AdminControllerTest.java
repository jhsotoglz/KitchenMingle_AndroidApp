package RoundTrip.Controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import RoundTrip.controller.AdminController;
import RoundTrip.model.Admin;
import RoundTrip.model.Users;
import RoundTrip.repository.AdminRepository;
import RoundTrip.repository.UsersRepository;
import org.testng.annotations.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Specify the JUnit runner to use for running the tests
@RunWith(SpringRunner.class)
// Use WebMvcTest to only start the web layer of the application
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    // Inject the MockMvc object for sending requests to the controller
    @Autowired
    private MockMvc mockMvc;

    // Create a mock instance of AdminRepository to be used in the test
    @MockBean
    private AdminRepository adminRepository;

    // Create a mock instance of UsersRepository to be used in the test
    @MockBean
    private UsersRepository usersRepository;

    // Test method for listing all admins
    @Test
    public void testGetAllAdmin() throws Exception {
        // Create an empty list to simulate the findAll() response
        List<Admin> adminList = new ArrayList<>();
        // When findAll() is called on the repository, return the empty list
        when(adminRepository.findAll()).thenReturn(adminList);

        // Perform a GET request to the "/admin" endpoint
        mockMvc.perform(get("/admin"))
                // Expect the request to be successful (HTTP 200 OK)
                .andExpect(status().isOk())
                // Expect the returned JSON to be an array with the same size as adminList
                .andExpect(jsonPath("$", hasSize(adminList.size())));
    }

    // Test method for getting an admin by ID
    @Test
    public void testGetAdminById() throws Exception {
        // Create a new Admin object and set its ID
        Admin admin = new Admin();
        admin.setId(1L);
        // When findById() is called with ID 1, return an Optional containing the admin
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        // Perform a GET request to the "/admin/getId/{id}" endpoint with ID 1
        mockMvc.perform(get("/admin/getId/{id}", 1L))
                // Expect the request to be successful (HTTP 200 OK)
                .andExpect(status().isOk())
                // Expect the returned JSON object to have an "id" attribute with a value of 1
                .andExpect(jsonPath("$.id", is(1)));
    }

    // Test method for getting an admin by email
    @Test
    public void testGetAdminByEmail() throws Exception {
        // Create a new Admin object and set its email
        Admin admin = new Admin();
        admin.setEmail("admin@example.com");
        // When findByEmail() is called with a specific email, return the admin
        when(adminRepository.findByEmail("admin@example.com")).thenReturn(admin);

        // Perform a GET request to the "/admin/getEmail/{email}" endpoint with the admin's email
        mockMvc.perform(get("/admin/getEmail/{email}", "admin@example.com"))
                // Expect the request to be successful (HTTP 200 OK)
                .andExpect(status().isOk())
                // Expect the returned JSON object to have an "email" attribute with the admin's email
                .andExpect(jsonPath("$.email", is("admin@example.com")));
    }

    // Test method for deleting a user by admin
    @Test
    public void testDeleteUsers() throws Exception {
        // Create a new Users object and set its ID
        Users user = new Users();
        user.setId(1L);
        // When findById() is called with ID 1, return an Optional containing the user
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
        // Do nothing when delete() is called on the repository (to simulate the delete operation)
        doNothing().when(usersRepository).delete(any(Users.class));

        // Perform a DELETE request to the "/admin/deleteUser/{id}" endpoint with ID 1
        mockMvc.perform(delete("/admin/deleteUser/{id}", 1L))
                // Expect the request to be successful (HTTP 200 OK)
                .andExpect(status().isOk());
    }

    // Test method for getting all users by admin
    @Test
    public void testGetAllUsers() throws Exception {
        // Create an empty list to simulate the findAll() response for users
        List<Users> userList = new ArrayList<>();
        // When findAll() is called on the usersRepository, return the empty list
        when(usersRepository.findAll()).thenReturn(userList);

        // Perform a GET request to the "/admin/viewUsers" endpoint
        mockMvc.perform(get("/admin/viewUsers"))
                // Expect the request to be successful (HTTP 200 OK)
                .andExpect(status().isOk())
                // Expect the returned JSON to be an array with the same size as userList
                .andExpect(jsonPath("$", hasSize(userList.size())));
    }

    // Test method for getting a user by ID by admin
    @Test
    public void testGetUsers() throws Exception {
        // Create a new Users object and set its ID
        Users user = new Users();
        user.setId(1L);
        // When findById() is called with ID 1, return an Optional containing the user
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

        // Perform a GET request to the "/admin/viewUsers/{id}" endpoint with ID 1
        mockMvc.perform(get("/admin/viewUsers/{id}", 1L))
                // Expect the request to be successful (HTTP 200 OK)
                .andExpect(status().isOk())
                // Expect the returned JSON object to have an "id" attribute with a value of 1
                .andExpect(jsonPath("$.id", is(1)));
    }
    
}

