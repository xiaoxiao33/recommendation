package com.se.controller;

import com.se.exception.DataServiceOperationException;
import com.se.repository.InvitationRepository;
import com.se.repository.ScheduleRepository;
import com.se.repository.UserProfileRepository;
import com.se.service.InvitationService;
import com.se.vo.InvitationBriefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invitation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvitationManagementController {

    @Autowired
    private InvitationService invitationService;

    /**
     * @param id
     * @return  invitation received by the user from others that needs to be processed.
     */
    @GetMapping("/pending/{id}")
    public ResponseEntity<List<InvitationBriefVO>> getPendingInvitations(@PathVariable("id") int id) {
        List<InvitationBriefVO> pendingInvites = invitationService.getPendingInvitation(id);
        return new ResponseEntity<>(pendingInvites, HttpStatus.OK);
    }

    /**
     * @param id
     * @return  invitations sent out by the user that wait for others' responses
     */
    @GetMapping("/waiting/{id}")
    public ResponseEntity<List<InvitationBriefVO>> getWaitingInvitations(@PathVariable("id") int id) {
        List<InvitationBriefVO> waitingInvites = invitationService.getWaitingInvitation(id);
        return new ResponseEntity<>(waitingInvites, HttpStatus.OK);

    }

    /**
     * @param //session
     * @return upcoming meals that have been confirmed by both the sender and the receiver
     */
    @GetMapping("/upcoming/{id}")
    public ResponseEntity<List<InvitationBriefVO>> getUpcomingInvitations(@PathVariable("id") int id) {
        List<InvitationBriefVO> upcomingInvites = invitationService.getUpcomingInvitation(id);
        return new ResponseEntity<>(upcomingInvites, HttpStatus.OK);
    }


    /**
     * @param invitationId
     * @return status code indicating if an invitation is accepted successfully
     */
    @PostMapping("/accept")
    public ResponseEntity<HttpStatus> acceptInvitation(@RequestParam("invitation_id") int invitationId) {
        // Check if the invitation is still active
        try {
            boolean succeeded = invitationService.acceptInvitation(invitationId);
            if (succeeded) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (DataServiceOperationException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
