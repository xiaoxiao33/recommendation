package com.se.controller;

import com.se.repository.UserInfoRepository;
import com.se.repository.UserProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

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



    @Test
    public void testGetAllUsers() throws Exception {
        String apiUrl = "/admin/users";
        Mockito.when(userInfoRepository.findAllInfo()).thenReturn(new ArrayList<>());

        // Build a GET Request and send it to the test server
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn(); // throws Exception

        // Validate response
        String tr = r.getResponse().getContentAsString();
        // System.out.println(tr);
        String  expected = "[]";

        assertEquals("REST API Returned incorrect response.", expected, tr);
    }
}
