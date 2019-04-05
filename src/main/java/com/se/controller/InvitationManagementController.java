package com.se.controller;

import com.alibaba.fastjson.JSON;
import com.se.exception.DataServiceOperationException;
import com.se.repository.InvitationRepository;
import com.se.repository.ScheduleRepository;
import com.se.service.InvitationService;
import com.se.util.InvitationStatus;
import com.se.vo.InvitationBriefVO;
import com.se.vo.InvitationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/invitation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvitationManagementController {

    private final InvitationRepository invitationRepository;
    private final ScheduleRepository scheduleRepository;

    private final static String PATTERN = "yyyy-MM-dd-HH-MM";

    public InvitationManagementController(InvitationRepository invitationRepository, ScheduleRepository scheduleRepository) {
        this.invitationRepository = invitationRepository;
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * @param id
     * @return  invitation received by the user from others that needs to be processed.
     */
    @GetMapping("/pending/{id}")
    public ResponseEntity<String> getReceivedInvitations(@PathVariable("id") int id) {
        List<InvitationVO> activeInvites =
                invitationRepository.getInvitationsByStatus(id, InvitationStatus.ACTIVE);
        List<InvitationBriefVO> pendingInvites = new ArrayList<>();
        for (InvitationVO invitationVo : activeInvites) {
//            System.out.println("id:" + id + " receiverId:" + invitationVo.getReceiverId());
            if (invitationVo.receiverId == id) {
                pendingInvites.add(new InvitationBriefVO(invitationVo));
            }
        }
        System.out.println(pendingInvites.size());
//        System.out.println(pendingInvites.get(0));
        String res = JSON.toJSONString(pendingInvites);
//        System.out.println(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * @param id
     * @return  invitations sent out by the user that wait for others' responses
     */
    @GetMapping("/waiting/{id}")
    public ResponseEntity<List<InvitationBriefVO>> getNonrespInvitations(@PathVariable("id") int id) {
        List<InvitationVO> activeInvites =

                invitationRepository.getInvitationsByStatus(id, InvitationStatus.ACTIVE);
        List<InvitationBriefVO> waitingInvites = new ArrayList<>();
        for (InvitationVO invitationVo : activeInvites) {
            if (invitationVo.senderId == id) {
                waitingInvites.add(new InvitationBriefVO(invitationVo));
            }
        }
        return new ResponseEntity<>(waitingInvites, HttpStatus.OK);

    }

    /**
     * @param //session
     * @return upcoming meals that have been confirmed by both the sender and the receiver
     */
    @GetMapping("/upcoming/{id}")
    public ResponseEntity<List<InvitationBriefVO>> getUpcomingInvitations(@PathVariable("id") int id) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        String date = simpleDateFormat.format(new Date());

        List<InvitationVO> acceptedInvites = invitationRepository.getAcceptedInvitationsByTime(id, date);
        List<InvitationBriefVO> upcomingInvites = new ArrayList<>();
        for (InvitationVO invitationVO : acceptedInvites) {
            upcomingInvites.add(new InvitationBriefVO(invitationVO));
        }

        return new ResponseEntity<>(upcomingInvites, HttpStatus.OK);
    }


    /**
     * @param invitationId
     * @return status code indicating if an invitation is accepted successfully
     */
    @PostMapping("/accept")
    public ResponseEntity<HttpStatus> acceptInvitation(@RequestParam("invitation_id") int invitationId) {
        // Check if the invitation is still active
        InvitationStatus status = invitationRepository.checkInvitationStatus(invitationId);

        if (status == InvitationStatus.ACTIVE) {
            InvitationVO currentInvite = invitationRepository.getInvitationById(invitationId);
            int receiverId = currentInvite.receiverId;
            int senderId = currentInvite.senderId;
            String startTime = currentInvite.start;
            String endTime = currentInvite.end;

            try {
                invitationRepository.setInvitationStatusAccepted(invitationId);
                invitationRepository.setInvitationStatusRejected(receiverId, startTime);
                invitationRepository.setInvitationStatusRejected(senderId, startTime);
                scheduleRepository.addSlot(receiverId, startTime, endTime);
                scheduleRepository.addSlot(senderId, startTime, endTime);
            } catch (DataServiceOperationException ex) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }

        // An invitation is no longer active
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        String date = simpleDateFormat.format(new Date());
        System.out.println(date);
    }
}
