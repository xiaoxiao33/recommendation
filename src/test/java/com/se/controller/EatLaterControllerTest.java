package com.se.controller;

import com.alibaba.fastjson.JSON;
import com.se.service.InvitationService;
import com.se.service.RecommendationService;
import com.se.vo.IntendVO;
import com.se.vo.UserBriefVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EatLaterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecommendationService recommendationService;
    @MockBean
    private InvitationService invitationService;

    /**
     * IntendVO:
     * @throws Exception
     */
    @Test
    public void getRecommendationList() throws Exception {
        IntendVO intendVO = new IntendVO(1, "2019-04-05-09-45", "2019-04-05-10-30");
        List<UserBriefVO> list = new ArrayList<>();
        Mockito.when(recommendationService.getRecommendation(intendVO, intendVO.userId)).thenReturn(list);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/eatLater/recommendation")
                .accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(intendVO))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(response.getErrorMessage(), null);
    }
}
