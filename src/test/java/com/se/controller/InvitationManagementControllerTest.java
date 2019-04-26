package com.se.controller;

import com.se.exception.DataServiceOperationException;
import com.se.service.InvitationService;
import com.se.vo.InvitationBriefVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InvitationManagementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvitationService invitationService;

    @Test
    public void testPendingInvitations() throws Exception {
        List<InvitationBriefVO> pendingInvites = new ArrayList<>();
        Mockito.when(invitationService.getPendingInvitation(1)).thenReturn(pendingInvites);
        mockMvc.perform(get("/invitation/pending/1")).andExpect(status().isOk());
    }


    @Test
    public void testWaitingInvitations() throws Exception {
        List<InvitationBriefVO> pendingInvites = new ArrayList<>();
        Mockito.when(invitationService.getWaitingInvitation(1)).thenReturn(pendingInvites);
        mockMvc.perform(get("/invitation/waiting/1")).andExpect(status().isOk());
    }

    @Test
    public void testUpcomingInvitations() throws Exception {
        List<InvitationBriefVO> pendingInvites = new ArrayList<>();
        Mockito.when(invitationService.getUpcomingInvitation(1)).thenReturn(pendingInvites);
        mockMvc.perform(get("/invitation/upcoming/1")).andExpect(status().isOk());
    }

    @Test
    public void testAcceptInvitations() throws Exception {

        Mockito.when(invitationService.acceptInvitation(1)).thenReturn(true);

        RequestBuilder rb = MockMvcRequestBuilders.post("/invitation/accept")
                .param("invitation_id", "1");

        MvcResult r = mockMvc.perform(rb).andReturn(); // throws Exception

        ResultMatcher ok = status().isOk();

        this.mockMvc.perform(rb).andExpect(ok);
    }


    @Test
    public void testAcceptInvitationsFail_Conflict() throws Exception {

        Mockito.when(invitationService.acceptInvitation(1)).thenReturn(false);

        RequestBuilder rb = MockMvcRequestBuilders.post("/invitation/accept")
                .param("invitation_id", "1");

        MvcResult r = mockMvc.perform(rb).andReturn(); // throws Exception

        ResultMatcher conflict = status().isConflict();

        this.mockMvc.perform(rb).andExpect(conflict);
    }

    @Test
    public void testAcceptInvitationsFail_NotPerformed() throws Exception {

        Mockito.when(invitationService.acceptInvitation(1)).thenThrow(DataServiceOperationException.class);

        RequestBuilder rb = MockMvcRequestBuilders.post("/invitation/accept")
                .param("invitation_id", "1");

        MvcResult r = mockMvc.perform(rb).andReturn(); // throws Exception

        ResultMatcher serverError = status().isInternalServerError();

        this.mockMvc.perform(rb).andExpect(serverError);
    }

}
