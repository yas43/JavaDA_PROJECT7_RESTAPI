package com.nnk.springboot.control;


import com.nnk.springboot.controllers.*;
import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private Utils utils;

//    @InjectMocks
//    private UserController userController;

    private UserDTO userDTO;
    private UserDTO NVuserDTO;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setUsername("testUser@9");
        userDTO.setFullname("Test User");
        userDTO.setPassword("12345678");
        userDTO.setRole("ADMIN");

        NVuserDTO = new UserDTO();
        NVuserDTO.setId(1);
        NVuserDTO.setUsername("testUser");
        NVuserDTO.setFullname("Test User");
        NVuserDTO.setPassword("123458");
        NVuserDTO.setRole("ADMIN");

        user = new User();
        user.setUsername("TestUser@9");
        user.setFullname("testUser");
        user.setPassword("12345678");
        user.setRole("ADMIN");
    }

    @Test
    @WithMockUser
    void testHome() throws Exception {
//        List<UserDTO> users = Arrays.asList(userDTO);
        when(userService.displayAllUser()).thenReturn(Collections.emptyList());
        when(utils.currentUser()).thenReturn(user);

        mockMvc.perform(get("/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
//                .andExpect(model().attribute("users", users))
                .andExpect(model().attribute("users", Collections.emptyList()));

        verify(userService, times(1)).displayAllUser();
        verify(utils, times(1)).currentUser();
    }

    @Test
    @WithMockUser
    void testAddUserForm() throws Exception {
        mockMvc.perform(get("/admin/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
//                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "user",roles = "ADMIN")
    void testValidate_Success() throws Exception {
//        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        mockMvc.perform(post("/admin/validate")
                        .with(csrf().asHeader())
                        .flashAttr("user", userDTO))
                .andExpect(status().isFound())
//                .andExpect(status().is3xxRedirection());
                .andExpect(view().name("redirect:/admin/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/admin/list"));

//        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userService, times(1)).addUser(any(UserDTO.class));
    }

    @Test
    @WithMockUser
    void testValidate_HasErrors() throws Exception {
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/admin/validate")
                        .with(csrf().asHeader())
                        .flashAttr("user", NVuserDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));

        verify(passwordEncoder, times(0)).encode(anyString());
        verify(userService, times(0)).addUser(any(UserDTO.class));
    }

    @Test
    @WithMockUser
    void testShowUpdateForm() throws Exception {
        when(userService.displayUserById(anyInt())).thenReturn(userDTO);

        mockMvc.perform(get("/admin/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("userDTO", userDTO));

        verify(userService, times(1)).displayUserById(anyInt());
    }

    @Test
    @WithMockUser
    void testUpdateUser_Success() throws Exception {
        when(userService.updateUser(any(Integer.class),any(UserDTO.class))).thenReturn(user);
        mockMvc.perform(post("/admin/update/1")
                        .with(csrf().asHeader())
                        .flashAttr("userDTO", userDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/list"));

        verify(userService, times(1)).updateUser(anyInt(), any(UserDTO.class));
    }

    @Test
    @WithMockUser
    void testUpdateUser_HasErrors() throws Exception {
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/admin/update/1")
                        .with(csrf().asHeader())
                        .flashAttr("user", userDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));

        verify(userService, times(0)).updateUser(anyInt(), any(UserDTO.class));
    }

    @Test
    @WithMockUser
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyInt());

        mockMvc.perform(get("/admin/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/list"));

        verify(userService, times(1)).deleteUser(anyInt());
    }
}

