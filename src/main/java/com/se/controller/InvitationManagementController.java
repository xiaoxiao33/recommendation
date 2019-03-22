package com.se.controller;

import com.se.service.InvitationService;
import com.se.vo.InvitationBriefVO;
import com.se.vo.InvitationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/invitation")
@CrossOrigin("http://localhost:8100")
public class InvitationManagementController {

    @Autowired
    private InvitationService invitationService;

    /**
     *
     * @param session
     * @return  别人发过来的invitation
     */
    @GetMapping("/pending")
    public ResponseEntity<List<InvitationBriefVO>> getReceivedInvitations(HttpSession session) {
        return null;
    }

    @GetMapping("/waiting")
    public ResponseEntity<List<InvitationBriefVO>> getNonrespInvitations(HttpSession session) {
        return null;
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<InvitationBriefVO>> getUpcomingInvitations(HttpSession session) {
        return null;
    }

    @GetMapping("/view")
    public ResponseEntity<InvitationVO> viewInvitationInfo(@RequestParam("invitation_id") int invitationId) {
        return null;
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptInvitation(@RequestParam("invitation_id") int invitationId) {
        return null;
    }



}
