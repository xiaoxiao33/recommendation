package com.se.repository;

import com.se.util.InvitationStatus;
import com.se.vo.InvitationVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository {

    // status default pending
    public boolean addInvitation(InvitationVO vo);

    public InvitationVO getInvitationById(int invitationId);

    public List<InvitationVO> getInvitationsByStatus(int uid, InvitationStatus status);

    public List<InvitationVO> getAcceptedInvitationsByTime(int uid, String currentTime);

    public boolean updateInvitationStatus(int uid, String start);

    public InvitationStatus checkInvitationStatus(int invitationId);

}
