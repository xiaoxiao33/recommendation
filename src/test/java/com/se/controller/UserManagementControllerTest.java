package com.se.controller;

import com.se.model.UserInfo;
import com.se.model.UserProfile;
import com.se.repository.UserInfoRepository;
import com.se.repository.UserProfileRepository;
import com.se.service.PasswordSecurityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.MockInjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.tree.ExpandVetoException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserManagementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileRepository userProfileRepository;

    @MockBean
    private UserInfoRepository userInfoRepository;

    @MockBean
    private PasswordSecurityService passwordSecurityService;

    private static String email = "123@yale.edu";
    private static String password = "123456";
    private static UserInfo userInfo = UserInfo.builder().password(PasswordSecurityService.hashPassword(password))
            .email(email).build();

    private static UserProfile userProfile = new UserProfile(userInfo);

    @Test
    public void testGetAllUsers() throws Exception {
        String apiUrl = "/admin/users";
        Mockito.when(userInfoRepository.findAllInfo()).thenReturn(new ArrayList<>());


        // Build a GET Request and send it to the test server
        RequestBuilder rb = get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn(); // throws Exception

        // Validate response
        String tr = r.getResponse().getContentAsString();
        // System.out.println(tr);
        String  expected = "[]";

        assertEquals("REST API Returned incorrect response.", expected, tr);
    }

    @Test
    public void registerSucceed() throws Exception {
        Mockito.when(userInfoRepository.findInfoByEmail(any())).thenReturn(Optional.empty());
        Mockito.when(userInfoRepository.saveInfo(any())).thenReturn(userInfo);

        RequestBuilder rb = MockMvcRequestBuilders.post("/register")
                .param("email", email)
                .param("password", password);

        MvcResult r = mockMvc.perform(rb).andReturn(); // throws Exception

        ResultMatcher created = status().isCreated();

        this.mockMvc.perform(rb).andExpect(created);

    }

    @Test
    public void registerFail_InvalidEmail() throws Exception {
        Mockito.when(userInfoRepository.findInfoByEmail(any())).thenReturn(Optional.empty());
        Mockito.when(userInfoRepository.saveInfo(any())).thenReturn(userInfo);

        RequestBuilder rb = MockMvcRequestBuilders.post("/register")
                .param("email", "hey@gmail.com")
                .param("password", password);

        MvcResult r = mockMvc.perform(rb).andReturn(); // throws Exception

        ResultMatcher badRequest = status().isBadRequest();

        this.mockMvc.perform(rb).andExpect(badRequest);

    }

    @Test
    public void registerFail_AlreadyExist() throws Exception {
        Mockito.when(userInfoRepository.findInfoByEmail(any())).thenReturn(Optional.ofNullable(userInfo));

        RequestBuilder rb = MockMvcRequestBuilders.post("/register")
                .param("email", email)
                .param("password", password);

        MvcResult r = mockMvc.perform(rb).andReturn(); // throws Exception

        ResultMatcher conflict = status().isConflict();

        this.mockMvc.perform(rb).andExpect(conflict);

    }

    @Test
    public void loginSucceed() throws Exception {
        Mockito.when(userInfoRepository.findInfoByEmail(any())).thenReturn(Optional.ofNullable(userInfo));
        RequestBuilder rb = MockMvcRequestBuilders.post("/login")
                .param("email", email)
                .param("password", password);
        MvcResult r = mockMvc.perform(rb).andReturn(); // throws Exception

        ResultMatcher ok = status().isOk();
        this.mockMvc.perform(rb).andExpect(ok);
    }


    @Test
    public void loginFail() throws Exception {
        Mockito.when(userInfoRepository.findInfoByEmail(any())).thenReturn(Optional.ofNullable(userInfo));
        RequestBuilder rb = MockMvcRequestBuilders.post("/login")
                .param("email", email)
                .param("password", "xxxxx");
        MvcResult r = mockMvc.perform(rb).andReturn(); // throws Exception

        ResultMatcher unauthorized = status().isUnauthorized();
        this.mockMvc.perform(rb).andExpect(unauthorized);

    }

    @Test
    public void getUserProfile() throws Exception {
        String apiUrl = "/userProfile/{id}";
        Mockito.when(this.userProfileRepository.findProfileById(1)).thenReturn(Optional.ofNullable(userProfile));
        mockMvc.perform(get("/userProfile/1")).andExpect(status().isOk());
    }

    @Test
    public void getUserProfileFail() throws Exception {
        Mockito.when(this.userProfileRepository.findProfileById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/userProfile/1")).andExpect(status().isNotFound());
    }

    @Test
    public void updateUserProfile() throws Exception {
        int id = 1;
        String gender = "f";
        String major = "cs";
        String college = "berkeley";
        String age = "23";
        String year = "junior";
        String availability = "T";
        String username = "Tony";
        String description = "minimalist";
        String gps = "T";

        Mockito.when(this.userProfileRepository.findProfileById(1)).thenReturn(Optional.ofNullable(userProfile));

        RequestBuilder rb = MockMvcRequestBuilders.post("/updateProfile")
                .param("uid", "1")
                .param("gender", gender)
                .param("major", major)
                .param("age", age)
                .param("year", year)
                .param("username", username)
                .param("description", description)
                .param("share_gps", gps)
                .param("college", college)
                .param("availability", availability);


        Mockito.when(this.userProfileRepository.updateProfile(any())).thenReturn(userProfile);
        ResultMatcher ok = status().isOk();
        this.mockMvc.perform(rb).andExpect(ok);

    }
}
