package com.se.controller;

import com.alibaba.fastjson.JSON;
import com.se.repository.LocationRepository;
import com.se.service.RecommendationService;
import com.se.vo.RealTimeLocationVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
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
public class EatNowControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecommendationService recommendationService;
    @MockBean
    private LocationRepository locationRepository;

    @Test
    public void testGetRealTimeRecommendation() throws Exception{
        RealTimeLocationVO vo = new RealTimeLocationVO();
        Mockito.when(recommendationService.getRealTimeRecommendation(vo)).thenReturn(new ArrayList<>());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/eatNow/recommendation")
                .accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(vo))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void testUploadLocation() throws Exception {
        RealTimeLocationVO vo = new RealTimeLocationVO();
        vo.uid = 1;
        Mockito.when(locationRepository.updateUserLocation(vo.latitude,vo.longitude,vo.uid,vo.lastUpdateTime)).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/eatNow/uploadLocation")
                .accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(vo))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
    }
}
