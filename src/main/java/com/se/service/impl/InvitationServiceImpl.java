package com.se.service.impl;

import com.se.exception.DataServiceOperationException;
import com.se.repository.InvitationRepository;
import com.se.repository.ScheduleRepository;
import com.se.repository.UserProfileRepository;
import com.se.service.InvitationService;
import com.se.util.InvitationStatus;
import com.se.vo.InvitationBriefVO;
import com.se.vo.InvitationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InvitationServiceImpl implements InvitationService {
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    private final static String PATTERN = "yyyy-MM-dd-HH-MM";

    @Override
    public boolean sendInvitation(InvitationVO vo) {
        if (invitationRepository.addInvitation(vo)) {
            return true;
        }
        return false;
    }

    @Override
    public List<InvitationBriefVO> getPendingInvitation(int id) {
        List<InvitationVO> activeInvites =
                invitationRepository.getInvitationsByStatus(id, InvitationStatus.ACTIVE);
        List<InvitationBriefVO> pendingInvites = new ArrayList<>();
        for (InvitationVO invitationVo : activeInvites) {
//            System.out.println("id:" + id + " receiverId:" + invitationVo.getReceiverId());
            if (invitationVo.receiverId == id) {
                pendingInvites.add(buildInvitationBriefVo(invitationVo));
            }
        }
        return pendingInvites;
    }


    @Override
    public List<InvitationBriefVO> getWaitingInvitation(int id) {
        List<InvitationVO> activeInvites =
                invitationRepository.getInvitationsByStatus(id, InvitationStatus.ACTIVE);
        List<InvitationBriefVO> waitingInvites = new ArrayList<>();
        for (InvitationVO invitationVo : activeInvites) {
            if (invitationVo.senderId == id) {
                waitingInvites.add(buildInvitationBriefVo(invitationVo));
            }
        }
        return waitingInvites;

    }

    @Override
    public List<InvitationBriefVO> getUpcomingInvitation(int id) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        String date = simpleDateFormat.format(new Date());

        List<InvitationVO> acceptedInvites = invitationRepository.getAcceptedInvitationsByTime(id, date);

        List<InvitationBriefVO> upcomingInvites = new ArrayList<>();
        for (InvitationVO invitationVO : acceptedInvites) {
            upcomingInvites.add(buildInvitationBriefVo(invitationVO));
        }

        return upcomingInvites;

    }

    @Override
    public boolean acceptInvitation(int invitationId) throws DataServiceOperationException {
        InvitationStatus status = invitationRepository.checkInvitationStatus(invitationId);
        // Check if the invitation is still active.
        if (status == InvitationStatus.ACTIVE) {
            InvitationVO currentInvite = invitationRepository.getInvitationById(invitationId);
            int receiverId = currentInvite.receiverId;
            int senderId = currentInvite.senderId;
            String startTime = currentInvite.start;
            String endTime = currentInvite.end;

            try {
                invitationRepository.setInvitationStatusAccepted(invitationId);
                invitationRepository.setInvitationStatusRejected(receiverId, startTime);
                scheduleRepository.addSlot(receiverId, startTime, endTime);
                scheduleRepository.addSlot(senderId, startTime, endTime);
            } catch (Exception ex) {
                throw new DataServiceOperationException("Fail to accept Invitation");
            }
            return true;
        }

        return false;
    }


    private InvitationBriefVO buildInvitationBriefVo(InvitationVO invitationVO) {
        InvitationBriefVO invitationBriefVO = new InvitationBriefVO(invitationVO);
        invitationBriefVO.rName = this.userProfileRepository.findProfileById(invitationVO.receiverId).get().getUsername();
        invitationBriefVO.sName = this.userProfileRepository.findProfileById(invitationVO.senderId).get().getUsername();
        return invitationBriefVO;
    }
}
