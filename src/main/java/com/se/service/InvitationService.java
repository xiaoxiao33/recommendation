package com.se.service;

import com.se.exception.DataServiceOperationException;
import com.se.vo.InvitationBriefVO;
import com.se.vo.InvitationVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InvitationService {

    public boolean sendInvitation(InvitationVO vo);

    public List<InvitationBriefVO> getWaitingInvitation(int id);

    public List<InvitationBriefVO> getPendingInvitation(int id);

    public List<InvitationBriefVO> getUpcomingInvitation(int id);

    public boolean acceptInvitation(int invitationId) throws DataServiceOperationException;

}
